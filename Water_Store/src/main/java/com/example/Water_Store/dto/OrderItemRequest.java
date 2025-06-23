package com.example.Water_Store.dto;



import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
