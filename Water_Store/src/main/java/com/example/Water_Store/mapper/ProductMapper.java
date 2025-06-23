package com.example.Water_Store.mapper;

import com.example.Water_Store.dto.ProductDto;
import com.example.Water_Store.entity.Category;
import com.example.Water_Store.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory().getId());
        dto.setSpecifications(product.getSpecifications());
        return dto;
    }

    public Product toEntity(ProductDto dto, Category category) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        product.setSpecifications(dto.getSpecifications());
        return product;
    }
}
