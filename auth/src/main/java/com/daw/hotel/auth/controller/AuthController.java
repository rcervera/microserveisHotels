package com.daw.hotel.auth.controller;

import com.daw.hotel.auth.dto.AuthRequest;
import com.daw.hotel.auth.dto.AuthResponse;
import com.daw.hotel.auth.model.User;
import com.daw.hotel.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            AuthResponse response = authService.register(user);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
