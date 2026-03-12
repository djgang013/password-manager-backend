package com.example.backend.vault.service;

import com.example.backend.vault.dto.LoginRequest;
import com.example.backend.vault.dto.LoginResponse;
import com.example.backend.vault.dto.RegisterRequest;
import com.example.backend.vault.dto.SaltResponse;
import com.example.backend.vault.entity.User;
import com.example.backend.vault.repository.UserRepository;
import com.example.backend.vault.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(RegisterRequest request){
        if (userRepository.findByUsername(request.username()).isPresent()){
            throw new RuntimeException("username already taken");
        }
        User user  =new User();
        user.setUsername(request.username());
        user.setSalt(request.salt());
        user.setMasterHash(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
}
public LoginResponse login(LoginRequest request){
        User user=userRepository.findByUsername(request.username())
                .orElseThrow(()->new RuntimeException("invalid usernam or password"));
    if (!passwordEncoder.matches(request.password(), user.getMasterHash())

    ) {
        throw new RuntimeException("Invalid username or password");

    }
    String token = jwtUtil.generateToken(user.getUsername());
    return new LoginResponse(token);
}
public com.example.backend.vault.dto.SaltResponse getSalt(String username){
        User user =userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));
        return new com.example.backend.vault.dto.SaltResponse(user.getSalt());
}

}
