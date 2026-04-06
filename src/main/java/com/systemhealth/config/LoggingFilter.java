package com.systemhealth.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();

        log.info("➡️ REQUEST: {} {}", req.getMethod(), req.getRequestURI());

        chain.doFilter(request, response);

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("✅ RESPONSE: {} {} | Time: {} ms",
                req.getMethod(),
                req.getRequestURI(),
                timeTaken);
    }
}