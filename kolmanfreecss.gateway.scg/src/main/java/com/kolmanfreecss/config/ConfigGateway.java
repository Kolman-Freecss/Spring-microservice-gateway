package com.kolmanfreecss.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGateway {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route(p -> p
//                        .path("/get")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://httpbin.org:80"))
                .route("paymentId", r -> r.path("/api/payment/**").uri("http://localhost:8763")) //static routing
                .route("orderId", r -> r.path("/api/order/**").uri("http://localhost:8762")) //dynamic routing
                .build();
    }

}
