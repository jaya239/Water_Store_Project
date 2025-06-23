package com.example.Water_Store.dto;


import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String currency; // e.g. "USD"
    private String orderId;
}
