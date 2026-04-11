package com.systemhealth.service;

import org.springframework.stereotype.Service;

@Service
public class SuggestionService {

    public String getSuggestion(String type) {

        return switch (type) {

            case "HIGH_CPU" ->
                    "Close heavy applications or allow system to terminate high CPU processes.";

            case "LOW_MEMORY" ->
                    "Clear background apps or increase virtual memory.";

            case "LOW_DISK" ->
                    "Delete unused files or clean temp folder.";

            default ->
                    "No suggestion available.";
        };
    }
}