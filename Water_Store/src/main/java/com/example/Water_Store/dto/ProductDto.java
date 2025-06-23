package com.example.Water_Store.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private Map<String, String> specifications;
}
