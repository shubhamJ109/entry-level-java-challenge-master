package com.challenge.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * A simple security interceptor to protect endpoints using an API Key.
 * Checks for a secret token in the "X-API-KEY" request header against
 * the configured value.
 */
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final String apiKey;

    /**
     * Constructor injecting the externalized API key.
     *
     * @param apiKey the expected secret key value
     */
    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientKey = request.getHeader("X-API-KEY");

        // Verify key matches the externalized application secret
        if (clientKey == null || !clientKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing X-API-KEY header");
        }

        return true; // Allow request to proceed to the controller
    }
}
