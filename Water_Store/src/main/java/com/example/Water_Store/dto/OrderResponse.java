package com.example.Water_Store.dto;


import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private String trackingId;
    private String status;
    private List<OrderItemRequest> items;
}
