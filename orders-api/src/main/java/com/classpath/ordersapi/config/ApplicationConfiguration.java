package com.classpath.ordersapi.config;

import com.classpath.ordersapi.repository.OrderRepository;
import com.classpath.ordersapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final OrderRepository orderRepository;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "initialize", havingValue = "true")
    public OrderService orderService(){
        return new OrderService(orderRepository);
    }

    @Bean
    @ConditionalOnMissingBean(name="orderService")
    public OrderService orderServiceAlternate(){
        return new OrderService(orderRepository);
    }
}