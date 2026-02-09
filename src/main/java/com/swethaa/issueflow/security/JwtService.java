package com.swethaa.issueflow.security;

import com.swethaa.issueflow.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@Service
public class JwtService {

    private final Key key;
    private final long expirationMs;

    public JwtService(@Value ("${app.jwt.secret}") String secret, @Value ("${app.jwt.expiration-ms}") Long expirationMs){
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMs = expirationMs;
    }

    public String generateToken(User user){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return parseClaims(token).getBody().getSubject();
    }

    public String extractRole(String token){
        Object role = parseClaims(token).getBody().get("role");
        return role == null?null : role.toString();
    }

    public boolean isTokenValid(String token){
        try{
            parseClaims(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}
