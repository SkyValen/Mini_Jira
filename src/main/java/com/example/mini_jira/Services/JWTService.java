package com.example.mini_jira.Services;

import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;
    private final UserRepository userRepository;

    public JWTService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("Id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
    public Long extractId(String token) {
        return Long.valueOf(getClaims(token).get("Id").toString());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
