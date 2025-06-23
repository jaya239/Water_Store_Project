package com.example.Water_Store.service;


import com.example.Water_Store.dto.OrderStatusStatsDto;
import com.example.Water_Store.dto.SalesReportDto;
import com.example.Water_Store.entity.Order;
import com.example.Water_Store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderRepository orderRepo;

    public List<SalesReportDto> getRevenueReport(String period) {
        List<Order> orders = orderRepo.findAll();

        return switch (period.toLowerCase()) {
            case "daily" -> aggregateByDate(orders);
            case "weekly" -> aggregateByWeek(orders);
            case "monthly" -> aggregateByMonth(orders);
            default -> throw new IllegalArgumentException("Invalid period");
        };
    }

    private List<SalesReportDto> aggregateByDate(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCreatedAt().toLocalDate(),
                        Collectors.summingDouble(o -> o.getItems().stream()
                                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum())))
                .entrySet().stream()
                .map(e -> new SalesReportDto(e.getKey(), e.getValue()))
                .toList();
    }

    private List<SalesReportDto> aggregateByWeek(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCreatedAt().toLocalDate().with(java.time.DayOfWeek.MONDAY),
                        Collectors.summingDouble(o -> o.getItems().stream()
                                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum())))
                .entrySet().stream()
                .map(e -> new SalesReportDto(e.getKey(), e.getValue()))
                .toList();
    }

    private List<SalesReportDto> aggregateByMonth(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCreatedAt().toLocalDate().withDayOfMonth(1),
                        Collectors.summingDouble(o -> o.getItems().stream()
                                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum())))
                .entrySet().stream()
                .map(e -> new SalesReportDto(e.getKey(), e.getValue()))
                .toList();
    }

    public List<OrderStatusStatsDto> getOrderStatusDistribution() {
        return orderRepo.findAll().stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new OrderStatusStatsDto(e.getKey(), e.getValue()))
                .toList();
    }
}
