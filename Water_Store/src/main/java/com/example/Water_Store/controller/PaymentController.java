package com.example.Water_Store.controller;


import com.example.Water_Store.dto.PaymentRequest;
import com.example.Water_Store.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest request) throws Exception {
        String approvalUrl = paymentService.createPayment(request);
        return ResponseEntity.ok(approvalUrl);
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam String trackingId) {
        return ResponseEntity.ok(paymentService.markOrderAsPaid(trackingId));
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment was cancelled.");
    }
}

