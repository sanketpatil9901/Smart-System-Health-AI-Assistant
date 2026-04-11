package com.systemhealth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;       // HIGH_CPU, LOW_MEMORY
    private String severity;   // LOW, MEDIUM, HIGH
    private String status;     // DETECTED, FIXED, FAILED
    private String suggestion;
    private String message;

    private Long timestamp;
}