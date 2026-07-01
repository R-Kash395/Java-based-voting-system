package com.example.voting.controller;

import com.example.voting.dto.VoteRequest;
import com.example.voting.service.VotingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/vote")
public class VotingController {

    private final VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    // Endpoint: POST http://localhost:8080/api/vote/cast
    @PostMapping("/cast")
    public ResponseEntity<String> castVote(@RequestBody VoteRequest request, Principal principal) {
        try {
            // Securely derive user identity directly from Spring Security context
            String authenticatedUser = principal.getName();
            String result = votingService.castBallot(authenticatedUser, request.candidateName());
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}