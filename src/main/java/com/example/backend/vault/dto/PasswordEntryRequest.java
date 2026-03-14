package com.example.backend.vault.dto;

public record PasswordEntryRequest(
        String websiteName,
        String websiteUrl,
        String loginUsername,
        String encryptedPassword,
        String iv
) {
}
