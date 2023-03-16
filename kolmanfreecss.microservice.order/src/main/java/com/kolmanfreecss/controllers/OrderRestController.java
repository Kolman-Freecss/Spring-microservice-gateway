package com.kolmanfreecss.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    
    @Value("${server.port}")
    private String port;
    
    @GetMapping("/info")
    public ResponseEntity<String> shwoOrderInfo() {
        return ResponseEntity.ok("Order service is running on port: " + port);
    }
    
}
