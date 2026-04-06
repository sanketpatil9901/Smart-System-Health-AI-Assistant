package com.systemhealth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        log.error("❌ ERROR: {}", ex.getMessage(), ex);
        return "Something went wrong";
    }
}