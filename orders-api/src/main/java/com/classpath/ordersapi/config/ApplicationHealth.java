package com.classpath.ordersapi.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
class RDSHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        //perform a query and verify if the db connection is up
        return Health.up().withDetail("DB-Service", "RDS is up").build();
    }
}

@Component
class KafkaHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        //perform a query and verify if the db connection is up
        return Health.up().withDetail("Kafka-Service", "Kafka is up").build();
    }
}
@Component
class PaymentGatewayHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        //perform a query and verify if the db connection is up
        return Health.up().withDetail("Payment-Gateway-Service", "Payment-Gateway service is up").build();
    }
}