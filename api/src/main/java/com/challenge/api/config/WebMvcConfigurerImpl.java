package com.challenge.api.config;

import com.challenge.api.security.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to register the {@link ApiKeyInterceptor} security interceptor.
 */
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    // Inject the externalized secret API key from application properties / environment
    @Value("${app.security.api-key}")
    private String apiKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Pass the injected key to the security interceptor
        registry.addInterceptor(new ApiKeyInterceptor(apiKey)).addPathPatterns("/api/v1/employee/**");
    }
}
