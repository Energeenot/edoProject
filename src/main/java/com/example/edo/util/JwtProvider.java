package com.example.edo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String getEmailFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            log.error("Error while take email  {}", e.getMessage());
            throw new RuntimeException("Invalid JWT in method getEmailFromToken");
        }
    }

    public String getRoleFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getClaim("role").asString();
        } catch (JWTVerificationException e) {
            log.error("Error while take role {}", e.getMessage());
            throw new RuntimeException("Invalid JWT in method getRoleFromToken");
        }
    }

    public String getUuidFromToken(String token) {
        try{
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getClaim("uuid").asString();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid JWT in method getUuidFromToken " + e);
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getExpiresAt();
            return expiration.before(new Date());
        } catch (JWTVerificationException e) {
            log.warn("Failed to parse JWT {}", e.getMessage(), e);
            return true;
        }
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-service")
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("JWT validation failed {}", e.getMessage());
            return false;
        }
    }
}
