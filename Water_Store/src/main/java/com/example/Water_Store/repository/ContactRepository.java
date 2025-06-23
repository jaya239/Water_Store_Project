package com.example.Water_Store.repository;

import com.example.Water_Store.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}