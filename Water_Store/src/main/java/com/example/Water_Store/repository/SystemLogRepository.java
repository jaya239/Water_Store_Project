package com.example.Water_Store.repository;

import com.example.Water_Store.entity.SystemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    List<SystemLog> findByType(String type);
    Page<SystemLog> findByType(String type, Pageable pageable);

}