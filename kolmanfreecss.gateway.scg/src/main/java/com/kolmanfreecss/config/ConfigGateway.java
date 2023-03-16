package com.kolmanfreecss.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ConfigGateway {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("paymentId", r -> r.path("/api/payment/**").uri("http://localhost:8763")) //static routing
                .route("orderId", r -> r.path("/api/order/**").uri("http://localhost:8762")) //dynamic routing
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/**").authenticated()
                .anyExchange().permitAll()
                .and().build();
    }
    
}
