package com.ascherty.app.utils;

import com.ascherty.app.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class will generate and validate JWT tokens.
 */

@Component
public class JwtUtil {
    @Value("${JWT_SECRET}")
    private String SECRET_KEY;

    private static final long EXPIRATION_TIME = 86400000;
    private Algorithm algorithm;

    @PostConstruct
    public void init() { // create algo after bean creation and key inserting
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(String username, Role role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role.name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public String extractRole(String token) {
        return JWT.decode(token).getClaim("role").asString();
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Updating (Refresh) token ? Blocked tokens (Logout)
}

