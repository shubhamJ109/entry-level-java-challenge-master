package com.challenge.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * A simple security interceptor to protect endpoints using an API Key.
 * Checks for a secret token in the "X-API-KEY" request header.
 */
public class ApiKeyInterceptor implements HandlerInterceptor {

    // Simple mock API key for demonstration purposes
    private static final String MOCK_API_KEY = "employees-r-us-secure-webhook-key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientKey = request.getHeader("X-API-KEY");

        if (clientKey == null || !clientKey.equals(MOCK_API_KEY)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing X-API-KEY header");
        }

        return true; // Allow request to proceed to the controller
    }
}
