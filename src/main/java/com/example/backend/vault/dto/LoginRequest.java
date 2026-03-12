package com.example.backend.vault.dto;

public record LoginRequest(
        String username,
        String password
) {}
