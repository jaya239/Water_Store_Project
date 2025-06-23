package com.example.Water_Store.service;


import com.example.Water_Store.dto.OrderItemRequest;
import com.example.Water_Store.dto.OrderRequest;
import com.example.Water_Store.dto.OrderResponse;
import com.example.Water_Store.entity.Order;
import com.example.Water_Store.entity.OrderItem;
import com.example.Water_Store.entity.Product;
import com.example.Water_Store.entity.User;
import com.example.Water_Store.repository.OrderRepository;
import com.example.Water_Store.repository.ProductRepository;
import com.example.Water_Store.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @Transactional
    public String placeOrder(OrderRequest request, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setTrackingId(UUID.randomUUID().toString());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepo.findById(item.getProductId()).orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        orderRepo.save(order);

        return order.getTrackingId();
    }

    public OrderResponse getOrderByTrackingId(String trackingId) {
        Order order = orderRepo.findByTrackingId(trackingId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

        OrderResponse response = new OrderResponse();
        response.setTrackingId(order.getTrackingId());
        response.setStatus(order.getStatus());

        List<OrderItemRequest> items = new ArrayList<>();
        for (OrderItem orderItem : order.getItems()) {
            OrderItemRequest dto = new OrderItemRequest();
            dto.setProductId(orderItem.getProduct().getId());
            dto.setQuantity(orderItem.getQuantity());
            items.add(dto);
        }

        response.setItems(items);
        return response;
    }
}

