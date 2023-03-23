package com.kolmanfreecss.application.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Autowired
    private Environment env;

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        // Get the context path (microservice gateway) from the exchange
        String contextPath = exchange.getRequest().getPath().contextPath().value();
        String headerName = env.getProperty("jwt.header.name");
        // Get the authorization header from the exchange
        String authHeader = exchange.getRequest().getHeaders().getFirst(headerName);
        
        if (authHeader == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        
        if (msAuth(contextPath, authHeader, "/payment", "payment.auth.header.value")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }
        
        if (msAuth(contextPath, authHeader, "/order", "order.auth.header.value")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }
        
        return chain.filter(exchange);
    }

    private boolean msAuth(String contextPath, String authHeader, String msPath, String msAuthHeaderValue) {
        return contextPath.equals(msPath) && authHeader.equals(env.getProperty(msAuthHeaderValue));
    }


}
