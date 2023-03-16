package com.kolmanfreecss.application.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentRestController {
    
    @Value("${server.port}")
    private String port;
    
    @GetMapping("/getPort")
    public ResponseEntity<String> showPaymentInfo() {
        return ResponseEntity.ok("Payment service is running on port: " + port);
    }
    
}
