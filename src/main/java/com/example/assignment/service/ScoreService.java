package com.example.assignment.service;

import com.example.assignment.model.Lead;
import com.example.assignment.model.ScoreLead;

import com.example.assignment.model.Offer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService {
    private final RuleScorer ruleScorer;
    private final AiScorer aiScorer;

    @Value("${OPENAI_API_KEY:}")
    private String openAiKey;

    public ScoreService(RuleScorer ruleScorer, AiScorer aiScorer) {
        this.ruleScorer = ruleScorer;
        this.aiScorer = aiScorer;
    }

    public List<ScoreLead> scoreAll(List<Lead> leads, Offer offer) {
        List<ScoreLead> out = new ArrayList<>();
        for (Lead lead : leads) {
            int ruleScore = ruleScorer.ruleScoreForLead(lead, offer);
            AiScorer.AiResult ai = aiScorer.scoreLead(lead, offer, openAiKey);
            int finalScore = Math.min(100, ruleScore + ai.getPoints());
            String reasoning = "RuleScore=" + ruleScore + "; AI: " + ai.getExplanation();
            ScoreLead s = new ScoreLead(lead, ai.getIntent(), finalScore, reasoning);
            out.add(s);
        }
        return out;
    }
}