package com.example.assignment.model;

public class ScoreLead extends Lead {
    private String intent;   // High, Medium, Low
    private int score;       // 0–100
    private String reasoning;

    public ScoreLead() { }

    public ScoreLead(Lead base, String intent, int score, String reasoning) {
        super(base.getName(), base.getRole(), base.getCompany(), base.getIndustry(),
                base.getLocation(), base.getLinkedinBio());
        this.intent = intent;
        this.score = score;
        this.reasoning = reasoning;
    }

    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }
}