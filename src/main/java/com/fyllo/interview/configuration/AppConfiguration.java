package com.fyllo.interview.configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class AppConfiguration {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create("https://postman-echo.com");
    }

    @Bean
    public RateLimiter getRateLimiter(RateLimiterConfig config) {
        return RateLimiter.of("postman-echo-rate-limiter", config);
    }

    @Bean
    public RateLimiterRegistry getRateLimiterRegistry(RateLimiterConfig config) {
        return RateLimiterRegistry.of(config);
    }

    @Bean
    public RateLimiterConfig getRateLimiterConfig(@Value("${limit.calls}") int limit) {
        return RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(limit)
                .timeoutDuration(Duration.ofMinutes(5 * limit))
                .build();
    }
}
