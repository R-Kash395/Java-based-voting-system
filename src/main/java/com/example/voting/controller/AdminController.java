package com.example.voting.controller;

import com.example.voting.dto.ElectionResult;
import com.example.voting.service.VotingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VotingService votingService;

    public AdminController(VotingService votingService) {
        this.votingService = votingService;
    }

    // Endpoint: GET http://localhost:8080/api/admin/results
    @GetMapping("/results")
    public ResponseEntity<ElectionResult> getElectionMetrics() {
        return ResponseEntity.ok(votingService.getLiveTallies());
    }
}