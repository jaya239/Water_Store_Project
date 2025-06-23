package com.example.Water_Store.controller;
import com.example.Water_Store.repository.OrderRepository;
import com.example.Water_Store.repository.SystemLogRepository;
import com.example.Water_Store.utils.CsvExportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin/export")
@RequiredArgsConstructor
public class AdminExportController {

    private final OrderRepository orderRepo;
    private final SystemLogRepository logRepo;


    @GetMapping("/orders")
    public ResponseEntity<InputStreamResource> exportOrdersToCsv() throws IOException {
        ByteArrayInputStream csv = CsvExportUtil.exportOrders(orderRepo.findAll());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csv));
    }

    @GetMapping("/sales")
    public ResponseEntity<InputStreamResource> exportSalesCsv() throws IOException {
        ByteArrayInputStream csv = CsvExportUtil.exportSalesReport(orderRepo.findAll());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=sales_report.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csv));
    }

    @GetMapping("/logs")
    public ResponseEntity<InputStreamResource> exportLogsCsv() throws IOException {
        ByteArrayInputStream csv = CsvExportUtil.exportSystemLogs(logRepo.findAll());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=system_logs.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csv));
    }


}

