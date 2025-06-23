package com.example.Water_Store.service;



import com.example.Water_Store.dto.ContactRequest;
import com.example.Water_Store.entity.Contact;
import com.example.Water_Store.repository.ContactRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepo;
    private final JavaMailSender mailSender;

    @Value("${contact.admin.email}")
    private String adminEmail;

    public void submitContact(ContactRequest request) {
        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setMessage(request.getMessage());
        contact.setSubmittedAt(LocalDateTime.now());
        contactRepo.save(contact);

        sendAdminNotification(contact);
    }

    private void sendAdminNotification(Contact contact) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(adminEmail);
            helper.setSubject("New Contact Submission");
            helper.setText("From: " + contact.getName() + "\n" +
                    "Email: " + contact.getEmail() + "\n\n" +
                    contact.getMessage());
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send contact email", e);
        }
    }
}
