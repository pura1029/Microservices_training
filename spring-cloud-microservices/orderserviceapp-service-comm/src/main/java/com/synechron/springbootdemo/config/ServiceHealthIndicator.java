package com.synechron.springbootdemo.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ServiceHealthIndicator implements HealthIndicator {
    private final String serviceKey = "Service-A";
    @Override
    public Health health() {
        return Health.up().withDetail(serviceKey, "Available").build();
    }
}

@Component
class ServiceBHealthIndicator implements HealthIndicator {
    private final String serviceKey = "Service-B";
    @Override
    public Health health() {
        return Health.up().withDetail(serviceKey, "Available").build();
    }
}