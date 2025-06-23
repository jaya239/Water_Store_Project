package com.example.Water_Store.service;

import com.example.Water_Store.dto.ProductDto;
import com.example.Water_Store.mapper.ProductMapper;
import com.example.Water_Store.repository.CategoryRepository;
import com.example.Water_Store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable).map(productMapper::toDto);
    }

    public Page<ProductDto> getByCategory(String categoryName, Pageable pageable) {
        return productRepo.findByCategoryNameIgnoreCase(categoryName, pageable)
                .map(productMapper::toDto);
    }

    public List<ProductDto> search(String keyword) {
        return productRepo.findByNameContainingIgnoreCase(keyword)
                .stream().map(productMapper::toDto).toList();
    }
}

