package com.daw.hotel.auth.security;


import com.daw.hotel.auth.model.User;
import com.daw.hotel.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // 🔑 Clau secreta (només per exemple, millor moure-la a application.yml)
    private static final String SECRET_KEY = "MySuperSecretKeyForJwtGeneration1234567890";

    // Duració del token (en mil·lisegons) — 1 hora
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Genera el token JWT
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        
        claims.put("roles", user.getRoles().stream()
            .map(Role::getName)
            .toList());

        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extreu el nom d’usuari del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Verifica si el token és vàlid
    public boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    // --- Mètodes privats auxiliars ---

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

