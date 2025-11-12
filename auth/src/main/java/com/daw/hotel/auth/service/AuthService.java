package com.daw.hotel.auth.service;

import com.daw.hotel.auth.model.User;
import com.daw.hotel.auth.repository.UserRepository;
import com.daw.hotel.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contrasenya incorrecta");
        }

        return jwtUtil.generateToken(user);
    }

    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("L'usuari ja existeix");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }
}

