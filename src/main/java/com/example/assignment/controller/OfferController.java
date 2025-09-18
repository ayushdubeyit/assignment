package com.example.assignment.controller;

import com.example.assignment.model.Offer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private Offer currentOffer;

    @PostMapping
    public ResponseEntity<String> setOffer(@RequestBody Offer offer) {
        if (offer == null || offer.getName() == null) {
            return ResponseEntity.badRequest().body("Offer must include name");
        }
        this.currentOffer = offer;
        return ResponseEntity.ok("Offer stored successfully");
    }

    @GetMapping
    public ResponseEntity<Offer> getOffer() {
        if (currentOffer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(currentOffer);
    }

    // Service layer me use ke liye
    public Offer getCurrentOfferInternal() {
        return currentOffer;
    }
}