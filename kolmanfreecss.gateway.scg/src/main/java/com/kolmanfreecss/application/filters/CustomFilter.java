package com.kolmanfreecss.application.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
public class CustomFilter implements GatewayFilter {

    @Value("${greetings}")
    private String greetings;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Custom pre filter");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Add custom headers
            exchange.getRequest().mutate().header("Custom-Header", greetings);
            System.out.println("Custom post filter added custom header, greetings: " + greetings);
        }));
    }
    
}
