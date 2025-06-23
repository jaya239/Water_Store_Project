package com.example.Water_Store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String endpoint;
    private String message;

    @Column(columnDefinition = "TEXT")  // 👈 allow long stack traces
    private String stackTrace;

    private LocalDateTime timestamp;
}
