package com.daw.hotel.auth.dto;

import java.util.List;

public class AuthResponse {
    public String token;
    public String username;
    public List<String> roles;

    public AuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}

