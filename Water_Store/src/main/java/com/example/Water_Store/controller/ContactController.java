package com.example.Water_Store.controller;



import com.example.Water_Store.dto.ContactRequest;
import com.example.Water_Store.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody ContactRequest request) {
        contactService.submitContact(request);
        return ResponseEntity.ok("Contact message sent successfully!");
    }
}

