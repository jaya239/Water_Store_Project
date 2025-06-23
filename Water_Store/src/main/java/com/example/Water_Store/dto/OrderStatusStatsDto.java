package com.example.Water_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusStatsDto {
    private String status;
    private Long count;
}
