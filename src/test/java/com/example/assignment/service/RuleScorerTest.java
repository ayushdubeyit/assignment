package com.example.assignment.service;

import com.example.assignment.model.Lead;
import com.example.assignment.model.Offer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleScorerTest {

    private final RuleScorer ruleScorer = new RuleScorer();

    @Test
    void testScoreRoleDecisionMaker() {
        Lead lead = new Lead("Ava", "CEO", "FlowMetrics", "SaaS", "Bengaluru", "CEO at FlowMetrics");
        int score = ruleScorer.scoreRole(lead.getRole());
        assertEquals(20, score, "CEO should score 20 points as decision-maker");
    }

    @Test
    void testScoreRoleInfluencer() {
        Lead lead = new Lead("Rohit", "Engineering Manager", "DevSolutions", "Software", "Noida", "Manager at DevSolutions");
        int score = ruleScorer.scoreRole(lead.getRole());
        assertEquals(10, score, "Manager should score 10 points as influencer");
    }

    @Test
    void testScoreIndustryExactMatch() {
        Offer offer = new Offer("AI SaaS", List.of("Automation"), List.of("SaaS"));
        Lead lead = new Lead("Sneha", "Founder", "Medix", "SaaS", "Bengaluru", "Founder of Medix");
        int score = ruleScorer.scoreIndustry(lead.getIndustry(), offer);
        assertEquals(20, score, "Exact industry match should give 20 points");
    }

    @Test
    void testScoreIndustryAdjacency() {
        Offer offer = new Offer("AI SaaS", List.of("Automation"), List.of("Healthcare"));
        Lead lead = new Lead("Sneha", "Founder", "Medix", "Biotech", "Bengaluru", "Founder of Medix");
        int score = ruleScorer.scoreIndustry(lead.getIndustry(), offer);
        assertEquals(10, score, "Adjacent industry should give 10 points");
    }

    @Test
    void testScoreCompletenessFullData() {
        Lead lead = new Lead("Ava", "CEO", "FlowMetrics", "SaaS", "Bengaluru", "CEO bio");
        int score = ruleScorer.scoreCompleteness(lead);
        assertEquals(10, score, "Complete data should give 10 points");
    }

    @Test
    void testScoreCompletenessMissingField() {
        Lead lead = new Lead("", "CEO", "FlowMetrics", "SaaS", "Bengaluru", "CEO bio");
        int score = ruleScorer.scoreCompleteness(lead);
        assertEquals(0, score, "Incomplete data should give 0 points");
    }

    @Test
    void testRuleScoreForLead() {
        Offer offer = new Offer("AI SaaS", List.of("Automation"), List.of("SaaS"));
        Lead lead = new Lead("Ava", "CEO", "FlowMetrics", "SaaS", "Bengaluru", "CEO bio");

        int score = ruleScorer.ruleScoreForLead(lead, offer);

        // role=20 + industry=20 + completeness=10 = 50
        assertEquals(50, score, "Full matching lead should score max 50 points");
    }
}