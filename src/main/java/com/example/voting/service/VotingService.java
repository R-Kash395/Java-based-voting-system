package com.example.voting.service;

import com.example.voting.dto.ElectionResult;
import com.example.voting.entity.Vote;
import com.example.voting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VotingService {

    @Autowired
    private VoteRepository voteRepository; // This is your bridge to MySQL!

    // Keep the list of valid candidates
    private final List<String> validCandidates = List.of("Candidate Alpha", "Candidate Beta");

    public synchronized String castBallot(String voterId, String candidateName) {
        // Fetch all votes currently saved in the database
        List<Vote> allVotes = voteRepository.findAll();

        // Step 1: Enforce single-vote protocol validation
        boolean alreadyVoted = allVotes.stream()
                                       .anyMatch(v -> v.getVoterId().equals(voterId));
        
        if (alreadyVoted) {
            throw new IllegalStateException("Security Alert: User has already cast a vote in this election cycle.");
        }

        // Step 2: Ensure target candidate exists
        if (!validCandidates.contains(candidateName)) {
            throw new IllegalArgumentException("Invalid selection: Target candidate not found.");
        }

        // Step 3: Commit transaction to MySQL atomically
        Vote newVote = new Vote();
        newVote.setVoterId(voterId);
        newVote.setCandidateName(candidateName);
        
        // This single line generates the SQL INSERT command and saves it forever!
        voteRepository.save(newVote);

        return "Ballot successfully authenticated and counted.";
    }

    public ElectionResult getLiveTallies() {
        // Fetch all votes directly from the MySQL database
        List<Vote> allVotes = voteRepository.findAll();
        
        Map<String, Integer> simpleTallies = new HashMap<>();
        
        // Initialize our valid candidates with 0 votes
        for (String candidate : validCandidates) {
            simpleTallies.put(candidate, 0);
        }

        // Count up the votes from the database records
        int total = 0;
        for (Vote vote : allVotes) {
            String candidate = vote.getCandidateName();
            if (simpleTallies.containsKey(candidate)) {
                simpleTallies.put(candidate, simpleTallies.get(candidate) + 1);
                total++;
            }
        }

        return new ElectionResult("General Election", simpleTallies, total);
    }
}