package com.systemhealth.controller;

import com.systemhealth.dto.*;
import com.systemhealth.entity.UserEntity;
import com.systemhealth.security.JwtUtil;
import com.systemhealth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        log.info("➡️ Signup attempt for username={}", request.getUsername());

        try {
            authService.register(request.getUsername(), request.getPassword());
            log.info("✅ Signup successful for username={}", request.getUsername());
            return "User registered successfully";

        } catch (Exception e) {
            log.error("❌ Signup failed for username={} | error={}",
                    request.getUsername(), e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        log.info("➡️ Login attempt for username={}", request.getUsername());
        try {
            UserEntity user = authService.login(request.getUsername());
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.warn("⚠️ Invalid password for username={}", request.getUsername());
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(user.getUsername());
            log.info("✅ Login successful for username={}", request.getUsername());

            return new AuthResponse(token);

        } catch (Exception e) {
            log.error("❌ Login failed for username={} | error={}",
                    request.getUsername(), e.getMessage(), e);
            throw e;
        }
    }
}