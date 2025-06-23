package com.example.Water_Store.controller;

import com.example.Water_Store.dto.ProductDto;
import com.example.Water_Store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    @GetMapping("/category/{name}")
    public Page<ProductDto> getByCategory(@PathVariable String name,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return productService.getByCategory(name, PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public List<ProductDto> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }
}
