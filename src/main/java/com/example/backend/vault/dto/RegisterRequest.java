package com.example.backend.vault.dto;

public record RegisterRequest(
        String username,
        String password,
        String salt
) {
}
