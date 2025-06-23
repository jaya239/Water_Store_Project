package com.example.Water_Store.controller;


import com.example.Water_Store.dto.OrderRequest;
import com.example.Water_Store.dto.OrderResponse;
import com.example.Water_Store.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request,
                                        HttpServletRequest httpRequest) {
        String email = httpRequest.getUserPrincipal().getName();
        String trackingId = orderService.placeOrder(request, email);
        return ResponseEntity.ok("Order placed with tracking ID: " + trackingId);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String trackingId) {
        return ResponseEntity.ok(orderService.getOrderByTrackingId(trackingId));
    }
}
