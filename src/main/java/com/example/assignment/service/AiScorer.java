package com.example.assignment.service;

import com.example.assignment.model.Lead;
import com.example.assignment.model.Offer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiScorer {
    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String,Integer> points = Map.of(
            "high", 50,
            "medium", 30,
            "low", 10
    );

    public AiResult scoreLead(Lead lead, Offer offer, String openAiApiKey) {
        if (openAiApiKey == null || openAiApiKey.isEmpty()) {
            return fallback("Medium", "No API key configured; defaulting to Medium", 30);
        }
        try {
            String prompt = buildPrompt(lead, offer);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(openAiApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4o-mini"); // change model if needed
            body.put("messages", List.of(Map.of("role","user","content", prompt)));
            body.put("temperature", 0);

            HttpEntity<Map<String,Object>> req = new HttpEntity<>(body, headers);
            ResponseEntity<String> resp = rest.postForEntity("https://api.openai.com/v1/chat/completions", req, String.class);

            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                return fallback("Medium", "AI call failed", 30);
            }

            JsonNode root = mapper.readTree(resp.getBody());
            String content = root.path("choices").get(0).path("message").path("content").asText();
            JsonNode parsed = mapper.readTree(content.trim());

            String intent = parsed.path("intent").asText("Medium");
            String explanation = parsed.path("explanation").asText("");
            int pts = points.getOrDefault(intent.toLowerCase(), 30);

            return new AiResult(intent, explanation, pts);

        } catch (Exception e) {
            return fallback("Medium", "Exception: " + e.getMessage(), 30);
        }
    }

    private AiResult fallback(String intent, String explanation, int pts) {
        return new AiResult(intent, explanation, pts);
    }

    private String buildPrompt(Lead lead, Offer offer) throws Exception {
        Map<String, Object> payload = Map.of(
                "offer", offer,
                "lead", lead,
                "instructions", "Classify buying intent as High, Medium, or Low. Return JSON: {\"intent\":\"...\",\"explanation\":\"...\"}"
        );
        return mapper.writeValueAsString(payload);
    }

    public static class AiResult {
        private final String intent;
        private final String explanation;
        private final int points;

        public AiResult(String intent, String explanation, int points) {
            this.intent = intent;
            this.explanation = explanation;
            this.points = points;
        }

        public String getIntent() { return intent; }
        public String getExplanation() { return explanation; }
        public int getPoints() { return points; }
    }
}