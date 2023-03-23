package com.kolmanfreecss.application.config;

import com.kolmanfreecss.application.filters.CustomFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGateway {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("paymentId", r -> 
                        r.path("/api/payment/**")
                        .filters(f -> f.filter(new CustomFilter()))
                        .uri("http://localhost:8763"))//static routing
                .route("orderId", r -> r.path("/api/order/**").uri("http://localhost:8762")) //dynamic routing
                .build();
    }

}
