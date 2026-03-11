package com.example.backend.vault.service;

import com.example.backend.vault.dto.RegisterRequest;
import com.example.backend.vault.entity.User;
import com.example.backend.vault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request){
        if (userRepository.findByUsername(request.username()).isPresent()){
            throw new RuntimeException("username already taken");
        }
        User user  =new User();
        user.setUsername(request.username());
        user.setSalt(request.salt());
        user.setMasterHash(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
}}
