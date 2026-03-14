package com.example.backend.vault.dto;

public record PasswordEntryResponse(
        long id,
        String websiteName,
        String websiteUrl,
        String loginUsername,
        String encryptedPassword,
        String iv
) {
}
