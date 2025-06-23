package com.example.Water_Store.utils;


import com.example.Water_Store.entity.SystemLog;
import com.example.Water_Store.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ActivityLogger {

    private final SystemLogRepository logRepo;

    public void log(String userAction, String endpoint) {
        SystemLog log = new SystemLog();
        log.setType("ACTIVITY");
        log.setEndpoint(endpoint);
        log.setMessage(userAction);
        log.setTimestamp(LocalDateTime.now());
        logRepo.save(log);
    }
}
