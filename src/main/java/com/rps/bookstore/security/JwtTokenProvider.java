package com.rps.bookstore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT Token Provider for generating and validating JWT tokens.
 * 
 * <p>This component handles JWT token operations including:
 * <ul>
 *   <li>Token generation from user authentication details</li>
 *   <li>Token validation and parsing</li>
 *   <li>Extracting user information from tokens</li>
 *   <li>Token expiration checking</li>
 * </ul>
 * 
 * <p>Token Structure:
 * <ul>
 *   <li>Header: Algorithm (HS256) and type (JWT)</li>
 *   <li>Payload: Subject (email), userId, role, issued at, expiration</li>
 *   <li>Signature: HMAC SHA256</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt.secret:your-secret-key-change-this-in-production-minimum-256-bits}")
    private String jwtSecret;
    
    @Value("${app.jwt.expiration:3600000}")
    private long jwtExpirationMs;
    
    /**
     * Generates a JWT token from user authentication.
     * 
     * @param authentication Spring Security authentication object
     * @return JWT token string
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("userId", userPrincipal.getId())
                .claim("role", userPrincipal.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * Generates a JWT token from user ID and email.
     * 
     * @param userId User ID
     * @param email User email
     * @param role User role
     * @return JWT token string
     */
    public String generateToken(Long userId, String email, com.rps.bookstore.entity.Role role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * Gets the signing key from the secret.
     * 
     * @return SecretKey for JWT signing
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Validates a JWT token.
     * 
     * @param token JWT token string
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets user ID from JWT token.
     * 
     * @param token JWT token string
     * @return User ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", Long.class);
    }
    
    /**
     * Gets username (email) from JWT token.
     * 
     * @param token JWT token string
     * @return Username (email)
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    
    /**
     * Gets user role from JWT token.
     * 
     * @param token JWT token string
     * @return User role
     */
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("role", String.class);
    }
}

