package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationHealth {
    @GetMapping("/check")
    public String getHealthCheck() {
        return "Application is running!!";
    }

    @GetMapping("/health")
    public String getHealth() {
        return "This is good!!";
    }
}
