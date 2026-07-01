package com.example.voting.service;

import com.example.voting.dto.ElectionResult;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VotingService {

    // Thread-safe dictionary tracking Candidates -> Vote Counts
    private final Map<String, AtomicInteger> candidateRegistry = new ConcurrentHashMap<>();
    
    // Thread-safe set tracking unique Voter IDs who already cast a ballot
    private final Set<String> historicalVoters = ConcurrentHashMap.newKeySet();

    public VotingService() {
        // Initialize default ballot choices
        candidateRegistry.put("Candidate Alpha", new AtomicInteger(0));
        candidateRegistry.put("Candidate Beta", new AtomicInteger(0));
    }

    public synchronized String castBallot(String voterId, String candidateName) {
        // Step 1: Enforce single-vote protocol validation
        if (historicalVoters.contains(voterId)) {
            throw new IllegalStateException("Security Alert: User has already cast a vote in this election cycle.");
        }

        // Step 2: Ensure target candidate exists
        if (!candidateRegistry.containsKey(candidateName)) {
            throw new IllegalArgumentException("Invalid selection: Target candidate not found.");
        }

        // Step 3: Commit transaction atomically
        historicalVoters.add(voterId);
        candidateRegistry.get(candidateName).incrementAndGet();

        return "Ballot successfully authenticated and counted.";
    }

    public ElectionResult getLiveTallies() {
        Map<String, Integer> simpleTallies = new ConcurrentHashMap<>();
        int total = 0;

        for (Map.Entry<String, AtomicInteger> entry : candidateRegistry.entrySet()) {
            int votes = entry.getValue().get();
            simpleTallies.put(entry.getKey(), votes);
            total += votes;
        }

        return new ElectionResult("General Election", simpleTallies, total);
    }
}