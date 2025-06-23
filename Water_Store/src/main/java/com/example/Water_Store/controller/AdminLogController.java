package com.example.Water_Store.controller;

import com.example.Water_Store.entity.SystemLog;
import com.example.Water_Store.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/logs")
@RequiredArgsConstructor
public class AdminLogController {

    private final SystemLogRepository logRepo;

    /**
     * Get paginated and sorted logs by type (e.g., ERROR, ACTIVITY).
     *
     * @param type    log type (ERROR, ACTIVITY)
     * @param page    page number (default 0)
     * @param size    number of records per page (default 10)
     * @param sortBy  sort field (default: timestamp)
     * @param order   asc or desc (default: desc)
     * @return paginated logs
     */
    @GetMapping("/errors")
    public ResponseEntity<Page<SystemLog>> getErrors(
            @RequestParam(defaultValue = "ERROR") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Pageable pageable = PageRequest.of(
                page, size,
                order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        return ResponseEntity.ok(logRepo.findByType(type.toUpperCase(), pageable));
    }

    /**
     * Get all activity logs (non-paginated) — optional utility.
     */
    @GetMapping("/activity")
    public ResponseEntity<Page<SystemLog>> getActivityLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(logRepo.findByType("ACTIVITY", pageable));
    }
}

