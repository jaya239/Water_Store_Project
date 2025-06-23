package com.example.Water_Store.controller;

import com.example.Water_Store.dto.CategoryDto;
import com.example.Water_Store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepo;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().map(cat -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            dto.setParentId(cat.getParent() != null ? cat.getParent().getId() : null);
            return dto;
        }).toList();
    }
}
