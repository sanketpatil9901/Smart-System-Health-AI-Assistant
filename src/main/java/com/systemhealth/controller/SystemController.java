package com.systemhealth.controller;

import com.systemhealth.entity.Issue;
import com.systemhealth.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
@Slf4j
public class SystemController {

    private final IssueService issueService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/issue")
    public String receiveIssue(@RequestBody Issue issue) {

        log.info("📥 Issue received: {}", issue.getType());
        Issue savedIssue = issueService.saveIssue(issue);
        // 🔥 SEND REAL-TIME ALERT
        messagingTemplate.convertAndSend("/topic/issues", savedIssue);
        log.info("📡 WebSocket alert sent for issue={}", issue.getType());
        return "Issue stored and alert sent";
    }
}