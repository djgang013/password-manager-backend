package com.example.backend.vault.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ping {
    public String ping(){return "vault backend is online";}
}
