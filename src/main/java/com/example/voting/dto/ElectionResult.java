package com.example.voting.dto;

import java.util.Map;

public record ElectionResult(String electionName, Map<String, Integer> tallies, int totalVotesCast) {}