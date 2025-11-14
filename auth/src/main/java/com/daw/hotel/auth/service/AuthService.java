package com.daw.hotel.auth.service;

import com.daw.hotel.auth.dto.AuthResponse;
import com.daw.hotel.auth.model.Role;
import com.daw.hotel.auth.model.User;
import com.daw.hotel.auth.repository.UserRepository;
import com.daw.hotel.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔹 LOGIN
    public AuthResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        if (!passwordEncoder.matches(password, user.password)) {
            throw new RuntimeException("Password incorrecte");
        }

        String token = jwtUtil.generateToken(user);
        List<String> roles = user.roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new AuthResponse(token, user.username, roles);
    }

    // 🔹 REGISTER
    public AuthResponse register(User user) {
        if (userRepository.findByUsername(user.username).isPresent()) {
            throw new RuntimeException("L’usuari ja existeix");
        }

        // Codifica el password
        user.password = passwordEncoder.encode(user.password);

        // Guarda l’usuari
        User savedUser = userRepository.save(user);

        // Genera token
        String token = jwtUtil.generateToken(savedUser);

        List<String> roles = savedUser.roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new AuthResponse(token, savedUser.username, roles);
    }
}
