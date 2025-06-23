package com.example.Water_Store.repository;


import com.example.Water_Store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByTrackingId(String trackingId);
}
