package com.challenge.api.config;

import com.challenge.api.security.ApiKeyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to register the {@link ApiKeyInterceptor} security interceptor.
 */
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Intercept all endpoints under /api/v1/employee
        registry.addInterceptor(new ApiKeyInterceptor()).addPathPatterns("/api/v1/employee/**");
    }
}
