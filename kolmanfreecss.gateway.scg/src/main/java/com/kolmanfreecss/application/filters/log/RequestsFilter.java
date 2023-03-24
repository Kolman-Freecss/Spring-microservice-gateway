package com.kolmanfreecss.application.filters.log;


import com.kolmanfreecss.domain.model.ApiParams;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@Log4j2
public class RequestsFilter implements WebFilter {

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        String traceId = null;
        Map<String, Object> trace = new LinkedHashMap<>();
        trace.put("url", request.getPath().toString());
        trace.put("method", request.getMethod());
        trace.put("headers", request.getHeaders());
        trace.put("parameters", request.getQueryParams());
        try {
            traceId = request.getHeaders().getFirst(ApiParams.HEADER_X_TRACEID.getValue());
            if (traceId == null) {
                traceId = UUID.randomUUID().toString();
                exchange.getResponse().getHeaders().add(ApiParams.HEADER_X_TRACEID.getValue(), traceId);
                exchange.getRequest().mutate().header(ApiParams.HEADER_X_TRACEID.getValue(), traceId).build();
            }
            log.info("--> START REQUEST ({}) --> OK - REQUEST: [{}]", traceId, trace);
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            trace.put("time", endTime - startTime);
            log.error("--> FINISH REQUEST ({}) --> ERROR! - REQUEST: [{}]", traceId, trace);
        }

        String finalTraceId = traceId;
        return chain.filter(exchange).doFinally(signalType -> {
            long endTime = System.currentTimeMillis();
            trace.put("time", endTime - startTime);
            log.info("--> FINISH REQUEST ({}) --> OK - REQUEST: [{}]", finalTraceId, trace);
        });
    }
    
}