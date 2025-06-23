package com.example.Water_Store.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(sender);
            helper.setSubject("Verify your Water Store account");
            helper.setText("Your OTP is: " + otp + "\n\nThis OTP will expire in 10 minutes.");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
