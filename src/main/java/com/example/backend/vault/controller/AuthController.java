package com.example.backend.vault.controller;

import com.example.backend.vault.dto.LoginRequest;
import com.example.backend.vault.dto.LoginResponse;
import com.example.backend.vault.dto.RegisterRequest;
import com.example.backend.vault.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
  private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<java.util.Map<String, String>> register(@RequestBody RegisterRequest request) {
        authService.register(request);

        return ResponseEntity.ok(java.util.Map.of("message", "User registered successfully"));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return  ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/salt/{username}")
    public ResponseEntity<com.example.backend.vault.dto.SaltResponse> getSalt(@PathVariable String username){
      return ResponseEntity.ok(authService.getSalt(username));
    }
}
