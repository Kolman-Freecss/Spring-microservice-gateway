package com.kolmanfreecss.application.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
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

        RequestPath path = exchange.getRequest().getPath();
        
        if (path.subPath(0, 2).value().equals("/actuator")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }
        
        String contextPath = path.subPath(3, 4).value();
        String headerName = env.getProperty("jwt.header.name");
        String prefixHeader = env.getProperty("jwt.api.manager.prefix");
        
        // Get the authorization header from the exchange
        String authHeader = exchange.getRequest().getHeaders().getFirst(headerName);
        
        if (authHeader == null || !authHeader.startsWith(prefixHeader)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        
        // Remove the prefix from the header
        authHeader = authHeader.replace(prefixHeader, "");
        
        if (msAuth(contextPath, authHeader, "payment", "jwt.api.manager.payment")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }
        
        if (msAuth(contextPath, authHeader, "order", "jwt.api.manager.order")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return chain.filter(exchange);
        }
        
        //Return 401 if the authorization header is not valid
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
        
    }

    private boolean msAuth(String contextPath, String authHeader, String msPath, String msAuthHeaderValue) {
        return contextPath.equals(msPath) && authHeader.equals(env.getProperty(msAuthHeaderValue));
    }


}
