package com.example.Water_Store.entity;

import com.example.Water_Store.utils.JsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String description;
    private Double price;
    private Integer stock;

    @ManyToOne
    private Category category;

    @Convert(converter = JsonConverter.class)
    private Map<String, String> specifications;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

