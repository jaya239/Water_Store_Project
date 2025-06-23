package com.example.Water_Store.controller;

import com.example.Water_Store.entity.Category;
import com.example.Water_Store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryRepository categoryRepo;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestParam String name,
                                            @RequestParam(required = false) Long parentId) {
        Category category = new Category();
        category.setName(name);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        if (parentId != null) {
            Optional<Category> parent = categoryRepo.findById(parentId);
            parent.ifPresent(category::setParent);
        }

        return ResponseEntity.ok(categoryRepo.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,
                                            @RequestParam String name) {
        Category category = categoryRepo.findById(id).orElseThrow();
        category.setName(name);
        category.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(categoryRepo.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
