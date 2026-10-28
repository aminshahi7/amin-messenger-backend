package com.aminmessenger.mymessenger.Jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {

    private final SecretKey key;
    private final Long expiration;

    public JwtService(@Value ("${jwt.secret}") String secret,
    @Value ("${jwt.expiration}") Long ex){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = ex;
    }
    
    public String generateToken(String email){
        
        Date now = new Date();

        return Jwts.builder()
            .subject(email)
            .signWith(key)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expiration))
            .claim("role", "USER")
            .compact();
    }

    public Claims parseToken(String token){

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String getUsername(String token){
        return parseToken(token).getSubject();
    }

    public boolean validToken(String token){
        try{
            parseToken(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }
    
}
