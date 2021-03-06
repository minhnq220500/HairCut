package com.example.haircut.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
public class JWTSecretKey {
    private final JWTConfig jwtConfig;

    @Autowired
    public JWTSecretKey(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
