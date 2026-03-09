package com.example.backend.vault.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String masterhash;

    @Column(,nullable = false)
    private String salt;

    private LocalDateTime createdAt = LocalDateTime.now();

}
