package com.example.assignment.controller;

import com.example.assignment.model.ScoreLead;

import com.example.assignment.service.ScoreService;
import com.example.assignment.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    private final ScoreService scoreService;
    private final CsvService csvService;
    private final OfferController offerController;

    public ScoreController(ScoreService scoreService, CsvService csvService, OfferController offerController) {
        this.scoreService = scoreService;
        this.csvService = csvService;
        this.offerController = offerController;
    }

    @PostMapping
    public ResponseEntity<String> runScoring() {
        if (offerController.getCurrentOfferInternal() == null) {
            return ResponseEntity.badRequest().body("No offer set; POST /offer first");
        }
        var results = scoreService.scoreAll(csvService.getLeads(), offerController.getCurrentOfferInternal());
        csvService.storeScored(results);
        return ResponseEntity.ok("Scored " + results.size() + " leads");
    }

    @GetMapping("/results")
    public ResponseEntity<List<ScoreLead>> getResults() {
        return ResponseEntity.ok(csvService.getScoredLeads());
    }
}