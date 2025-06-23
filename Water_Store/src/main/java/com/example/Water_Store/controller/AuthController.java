package com.example.Water_Store.controller;

import com.example.Water_Store.dto.LoginRequest;
import com.example.Water_Store.dto.RegisterRequest;
import com.example.Water_Store.enums.Role;
import com.example.Water_Store.entity.User;
import com.example.Water_Store.repository.UserRepository;
import com.example.Water_Store.service.EmailService;
import com.example.Water_Store.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        User user = User.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .isVerified(false)
                .otp(otp)
                .otpGeneratedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);

        return ResponseEntity.ok("Registered successfully. Check email for OTP.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        User user = optionalUser.get();

        if (!user.getOtp().equals(otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        if (user.getOtpGeneratedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("OTP expired");
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpGeneratedAt(null);
        userRepository.save(user);

        return ResponseEntity.ok("Account verified successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        if (!user.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not verified");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
