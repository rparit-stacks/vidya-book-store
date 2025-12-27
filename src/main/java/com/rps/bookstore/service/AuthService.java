package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.LoginRequest;
import com.rps.bookstore.dto.request.RefreshTokenRequest;
import com.rps.bookstore.dto.response.AuthResponse;

/**
 * Service interface for authentication operations.
 * 
 * <p>This service handles user authentication including:
 * <ul>
 *   <li>User login and token generation</li>
 *   <li>Token refresh operations</li>
 *   <li>User authentication validation</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public interface AuthService {
    
    /**
     * Authenticates a user and returns JWT token.
     * 
     * @param loginRequest Login credentials (email and password)
     * @return AuthResponse containing JWT token and user information
     */
    AuthResponse authenticate(LoginRequest loginRequest);
    
    /**
     * Refreshes a JWT token using refresh token.
     * 
     * @param refreshTokenRequest Refresh token request
     * @return AuthResponse containing new JWT token
     */
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}

