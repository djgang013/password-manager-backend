package com.example.backend.vault.controller;

import com.example.backend.vault.dto.PasswordEntryRequest;
import com.example.backend.vault.dto.PasswordEntryResponse;
import com.example.backend.vault.service.VaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vault")
@RequiredArgsConstructor
public class VaultController {
    private final VaultService vaultService;

    @PostMapping("/entries")
    public ResponseEntity<PasswordEntryResponse> addPassword(@RequestBody PasswordEntryRequest request){
        return ResponseEntity.ok(vaultService.addPassword(request));
    }
    @GetMapping("/entries")
    public ResponseEntity<List<PasswordEntryResponse>> getMyVault(){
        return ResponseEntity.ok(vaultService.getMyVault());


    }
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        vaultService.deletePassword(id);
        return ResponseEntity.ok().build();
    }
}
