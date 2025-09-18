package com.example.assignment.service;

import com.example.assignment.model.Lead;
import com.example.assignment.model.Offer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RuleScorer {

    private final List<String> decisionMakerKeywords = Arrays.asList(
            "ceo","founder","head of","vp","vice president","director","chief","cto","cfo","co-founder","president"
    );
    private final List<String> influencerKeywords = Arrays.asList(
            "manager","lead","senior","principal","growth"
    );

    public int scoreRole(String role) {
        if (role == null) return 0;
        String r = role.toLowerCase();
        for (String k : decisionMakerKeywords) {
            if (r.contains(k)) return 20;
        }
        for (String k : influencerKeywords) {
            if (r.contains(k)) return 10;
        }
        return 0;
    }

    public int scoreIndustry(String industry, Offer offer) {
        if (industry == null || offer == null || offer.getIdealUseCases() == null) return 0;
        String i = industry.toLowerCase();
        for (String target : offer.getIdealUseCases()) {
            if (target == null) continue;
            String t = target.toLowerCase();
            if (i.contains(t) || t.contains(i)) return 20; // exact/close
        }
        return 10; // adjacency
    }

    public int scoreCompleteness(Lead lead) {
        if (lead == null) return 0;
        boolean complete = notEmpty(lead.getName()) &&
                notEmpty(lead.getRole()) &&
                notEmpty(lead.getCompany()) &&
                notEmpty(lead.getIndustry()) &&
                notEmpty(lead.getLocation()) &&
                notEmpty(lead.getLinkedinBio());
        return complete ? 10 : 0;
    }

    private boolean notEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public int ruleScoreForLead(Lead lead, Offer offer) {
        return scoreRole(lead.getRole()) +
                scoreIndustry(lead.getIndustry(), offer) +
                scoreCompleteness(lead); // max 50
    }
}