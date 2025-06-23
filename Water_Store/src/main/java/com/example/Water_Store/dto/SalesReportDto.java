package com.example.Water_Store.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
public class SalesReportDto {
    private LocalDate date;
    private Double totalRevenue;
}

