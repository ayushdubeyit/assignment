package com.example.assignment.controller;

import com.example.assignment.model.Lead;
import com.example.assignment.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final CsvService csvService;

    public LeadController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            List<Lead> leads = csvService.parseCsv(file);
            csvService.storeLeads(leads);
            return ResponseEntity.ok("Uploaded " + leads.size() + " leads");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to parse CSV: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Lead>> getLeads() {
        return ResponseEntity.ok(csvService.getLeads());
    }
}