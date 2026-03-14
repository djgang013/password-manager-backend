package com.example.backend.vault.service;

import com.example.backend.vault.dto.PasswordEntryRequest;
import com.example.backend.vault.dto.PasswordEntryResponse;
import com.example.backend.vault.entity.PasswordEntry;
import com.example.backend.vault.entity.User;
import com.example.backend.vault.repository.PasswordEntryRepository;
import com.example.backend.vault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaultService {

    private final PasswordEntryRepository passwordEntryRepository;
    private final UserRepository userRepository;

    // Helper method to securely get the logged-in user from the JWT Token
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }

    // 1. Save a new encrypted password to the Vault
    public PasswordEntryResponse addPassword(PasswordEntryRequest request) {
        User currentUser = getAuthenticatedUser();

        PasswordEntry entry = new PasswordEntry();
        entry.setUser(currentUser);
        entry.setWebsiteName(request.websiteName());
        entry.setWebsiteUrl(request.websiteUrl());
        entry.setLoginUsername(request.loginUsername());

        // This is the Zero-Knowledge part: Spring Boot has no idea what this string actually says!
        entry.setEncryptedPassword(request.encryptedPassword());
        entry.setIv(request.iv());

        PasswordEntry savedEntry = passwordEntryRepository.save(entry);
        return mapToResponse(savedEntry);
    }

    // 2. Fetch all encrypted passwords for the logged-in user
    public List<PasswordEntryResponse> getMyVault() {
        User currentUser = getAuthenticatedUser();

        return passwordEntryRepository.findAllByUserId(currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper to map the Database Entity back to a clean JSON DTO
    private PasswordEntryResponse mapToResponse(PasswordEntry entry) {
        return new PasswordEntryResponse(
                entry.getId(),
                entry.getWebsiteName(),
                entry.getWebsiteUrl(),
                entry.getLoginUsername(),
                entry.getEncryptedPassword(),
                entry.getIv()
        );
    }
    // 3. Delete a password from the Vault (with security check!)
    public void deletePassword(Long entryId) {
        User currentUser = getAuthenticatedUser();

        PasswordEntry entry = passwordEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Password not found"));

        // SECURITY: Ensure the person deleting it actually owns it!
        if (!entry.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized: You do not own this password");
        }

        passwordEntryRepository.delete(entry);
    }
}