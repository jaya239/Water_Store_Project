package com.example.Water_Store.repository;

import com.example.Water_Store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryNameIgnoreCase(String categoryName, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
