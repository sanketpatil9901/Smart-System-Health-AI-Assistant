package com.systemhealth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemHealthService {

    private final RestTemplate restTemplate;

    public int calculateScore(int cpu, int memory, int disk) {

        int score = 100;

        if (cpu > 80) score -= 30;
        if (memory > 80) score -= 30;
        if (disk > 80) score -= 20;

        return Math.max(score, 0);
    }

    public Map<String, Object> getSystemStats() {

        Map<String, Object> stats = restTemplate.getForObject(
                "http://localhost:8001/system/stats",
                Map.class
        );

        return stats;
    }
}
