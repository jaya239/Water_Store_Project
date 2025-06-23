package com.example.Water_Store.controller;



import com.example.Water_Store.dto.ProductDto;
import com.example.Water_Store.entity.Category;
import com.example.Water_Store.entity.Product;
import com.example.Water_Store.mapper.ProductMapper;
import com.example.Water_Store.repository.CategoryRepository;
import com.example.Water_Store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody ProductDto dto) {
        Product product = productRepo.findById(id).orElseThrow();
        Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        product.setSpecifications(dto.getSpecifications());
        product.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(productRepo.save(product));
    }
}
