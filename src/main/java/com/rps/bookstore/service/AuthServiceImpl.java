package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.LoginRequest;
import com.rps.bookstore.dto.request.RefreshTokenRequest;
import com.rps.bookstore.dto.response.AuthResponse;
import com.rps.bookstore.dto.response.UserDTO;
import com.rps.bookstore.entity.User;
import com.rps.bookstore.exception.AccountDisabledException;
import com.rps.bookstore.exception.InvalidCredentialsException;
import com.rps.bookstore.repository.UserRepository;
import com.rps.bookstore.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AuthService for user authentication.
 * 
 * <p>This service implements authentication logic including:
 * <ul>
 *   <li>User login with email and password validation</li>
 *   <li>JWT token generation</li>
 *   <li>Token refresh operations</li>
 *   <li>Password validation</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Authenticates a user and returns JWT token.
     * 
     * @param loginRequest Login credentials
     * @return AuthResponse with JWT token and user info
     * @throws InvalidCredentialsException if credentials are invalid
     * @throws AccountDisabledException if account is disabled
     */
    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        
        if (!user.getEnabled()) {
            throw new AccountDisabledException("Account is disabled");
        }
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        
        UserDTO userDTO = mapToDTO(user);
        
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .expiresIn(3600L)
                .user(userDTO)
                .build();
    }
    
    /**
     * Refreshes a JWT token.
     * 
     * @param refreshTokenRequest Refresh token request
     * @return AuthResponse with new JWT token
     */
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        // For now, token refresh uses the same token
        // In a full implementation, you would validate the refresh token separately
        String token = refreshTokenRequest.getRefreshToken();
        
        if (!tokenProvider.validateToken(token)) {
            throw new InvalidCredentialsException("Invalid refresh token");
        }
        
        Long userId = tokenProvider.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        
        String newToken = tokenProvider.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole()
        );
        
        return AuthResponse.builder()
                .token(newToken)
                .type("Bearer")
                .expiresIn(3600L)
                .build();
    }
    
    /**
     * Maps User entity to UserDTO.
     * 
     * @param user User entity
     * @return UserDTO
     */
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .build();
    }
}

