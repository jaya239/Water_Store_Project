package com.example.Water_Store.config;


import com.example.Water_Store.entity.SystemLog;
import com.example.Water_Store.repository.SystemLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SystemLogRepository systemLogRepo;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, HttpServletRequest request) {
        SystemLog log = new SystemLog();
        log.setType("ERROR");
        log.setMessage(ex.getMessage());
        log.setEndpoint(request.getRequestURI());
        log.setStackTrace(Arrays.toString(ex.getStackTrace()));
        log.setTimestamp(LocalDateTime.now());

        systemLogRepo.save(log);

        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
