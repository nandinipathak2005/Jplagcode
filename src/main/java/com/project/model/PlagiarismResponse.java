package com.project.model;
import java.util.List;
import java.util.Map;

public record PlagiarismResponse(
    String jobId,
    Map<String, Double> peerMatches,
    List<String> detailedLogs,
    String aiInsights
) {}