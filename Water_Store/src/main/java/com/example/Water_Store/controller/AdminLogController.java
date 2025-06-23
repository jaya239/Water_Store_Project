package com.example.Water_Store.controller;



import com.example.Water_Store.entity.SystemLog;
import com.example.Water_Store.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/logs")
@RequiredArgsConstructor
public class AdminLogController {

    private final SystemLogRepository logRepo;

    @GetMapping("/errors")
    public List<SystemLog> getErrors(@RequestParam(defaultValue = "ALL") String severity) {
        return switch (severity.toUpperCase()) {
            case "CRITICAL", "ERROR" -> logRepo.findByType("ERROR");
            case "ACTIVITY" -> logRepo.findByType("ACTIVITY");
            default -> logRepo.findAll();
        };
    }

    @GetMapping("/activity")
    public List<SystemLog> getAllActivities() {
        return logRepo.findByType("ACTIVITY");
    }
}
