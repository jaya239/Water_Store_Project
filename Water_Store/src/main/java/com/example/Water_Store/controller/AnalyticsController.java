package com.example.Water_Store.controller;



import com.example.Water_Store.dto.SalesReportDto;
import com.example.Water_Store.dto.OrderStatusStatsDto;
import com.example.Water_Store.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<List<SalesReportDto>> getSalesReport(@RequestParam(defaultValue = "daily") String period) {
        return ResponseEntity.ok(analyticsService.getRevenueReport(period));
    }

    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatusStatsDto>> getOrderStatusStats() {
        return ResponseEntity.ok(analyticsService.getOrderStatusDistribution());
    }
}
