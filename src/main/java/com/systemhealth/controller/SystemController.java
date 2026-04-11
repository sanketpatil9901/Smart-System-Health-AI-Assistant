package com.systemhealth.controller;

import com.systemhealth.dto.SystemHealthResponse;
import com.systemhealth.entity.Issue;
import com.systemhealth.service.IssueService;
import com.systemhealth.service.SuggestionService;
import com.systemhealth.service.SystemHealthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
@Slf4j
public class SystemController {

    private final IssueService issueService;
    private final SimpMessagingTemplate messagingTemplate;
    private final SuggestionService suggestionService;
    private final SystemHealthService healthService;

    @PostMapping("/issue")
    public String receiveIssue(@RequestBody Issue issue) {

        log.info("📥 Issue received: {}", issue.getType());

        // Add suggestion
        String suggestion = suggestionService.getSuggestion(issue.getType());
        issue.setSuggestion(suggestion);
        Issue savedIssue = issueService.saveIssue(issue);
        // 🔥 SEND REAL-TIME ALERT
        messagingTemplate.convertAndSend("/topic/issues", savedIssue);
        log.info("📡 WebSocket alert sent for issue={}", issue.getType());
        return "Issue stored and alert sent";
    }

    @GetMapping("/health")
    public SystemHealthResponse getHealth() {

        Map<String, Object> stats = healthService.getSystemStats();

        int cpu = ((Number) stats.get("cpu")).intValue();
        int memory = ((Number) stats.get("memory")).intValue();
        int disk = ((Number) stats.get("disk")).intValue();

        int score = healthService.calculateScore(cpu, memory, disk);

        return new SystemHealthResponse(cpu, memory, disk, score);
    }
}