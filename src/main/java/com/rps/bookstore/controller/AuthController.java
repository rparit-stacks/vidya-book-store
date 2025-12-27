package com.rps.bookstore.controller;

import com.rps.bookstore.dto.request.LoginRequest;
import com.rps.bookstore.dto.request.RefreshTokenRequest;
import com.rps.bookstore.dto.request.RegisterRequest;
import com.rps.bookstore.dto.response.AuthResponse;
import com.rps.bookstore.dto.response.UserDTO;
import com.rps.bookstore.service.AuthService;
import com.rps.bookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for authentication endpoints.
 * 
 * <p>This controller handles authentication-related operations including:
 * <ul>
 *   <li>User registration</li>
 *   <li>User login and JWT token generation</li>
 *   <li>Token refresh operations</li>
 * </ul>
 * 
 * <p>All endpoints in this controller are public (no authentication required).
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;
    
    /**
     * Registers a new user account.
     * 
     * <p>Endpoint: POST /api/auth/register
     * 
     * @param registerRequest Registration request with user details
     * @return ResponseEntity with created user DTO
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    
    /**
     * Authenticates a user and returns JWT token.
     * 
     * <p>Endpoint: POST /api/auth/login
     * 
     * @param loginRequest Login credentials (email and password)
     * @return ResponseEntity with authentication response containing JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticate(loginRequest);
        return ResponseEntity.ok(authResponse);
    }
    
    /**
     * Refreshes a JWT token.
     * 
     * <p>Endpoint: POST /api/auth/refresh
     * 
     * @param refreshTokenRequest Refresh token request
     * @return ResponseEntity with new authentication response containing new JWT token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authResponse);
    }
}

