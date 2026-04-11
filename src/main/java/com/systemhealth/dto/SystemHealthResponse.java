package com.systemhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemHealthResponse {

    private int cpu;
    private int memory;
    private int disk;

    private int healthScore;
}