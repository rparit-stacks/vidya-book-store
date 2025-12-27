package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.RegisterRequest;
import com.rps.bookstore.dto.request.UpdateProfileRequest;
import com.rps.bookstore.dto.request.UpdateUserRoleRequest;
import com.rps.bookstore.dto.response.UserDTO;
import com.rps.bookstore.entity.Role;
import com.rps.bookstore.entity.User;
import com.rps.bookstore.exception.EmailAlreadyExistsException;
import com.rps.bookstore.exception.UserNotFoundException;
import com.rps.bookstore.repository.UserRepository;
import com.rps.bookstore.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserService for user management operations.
 * 
 * <p>This service implements all user-related business logic including:
 * <ul>
 *   <li>User registration with email validation</li>
 *   <li>Password encryption using BCrypt</li>
 *   <li>User profile management</li>
 *   <li>User role management</li>
 *   <li>User queries with pagination and filtering</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Registers a new user account.
     * 
     * @param request Registration request
     * @return UserDTO of created user
     * @throws EmailAlreadyExistsException if email already exists
     */
    @Override
    public UserDTO registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        
        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
        
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }
    
    /**
     * Gets the current authenticated user's profile.
     * 
     * @return UserDTO of current user
     * @throws UserNotFoundException if user not found
     */
    @Override
    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        UserPrincipal userPrincipal = getCurrentUserPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userPrincipal.getId()));
        return mapToDTO(user);
    }
    
    /**
     * Updates the current authenticated user's profile.
     * 
     * @param request Update profile request
     * @return Updated UserDTO
     * @throws UserNotFoundException if user not found
     */
    @Override
    public UserDTO updateProfile(UpdateProfileRequest request) {
        UserPrincipal userPrincipal = getCurrentUserPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userPrincipal.getId()));
        
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }
    
    /**
     * Gets user details by ID.
     * 
     * @param id User ID
     * @return UserDTO
     * @throws UserNotFoundException if user not found
     */
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }
    
    /**
     * Updates user role (Owner only).
     * 
     * @param userId User ID
     * @param request Update role request
     * @return Updated UserDTO
     * @throws UserNotFoundException if user not found
     */
    @Override
    public UserDTO updateUserRole(Long userId, UpdateUserRoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        user.setRole(request.getRole());
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }
    
    /**
     * Gets paginated list of all users with optional filters.
     * 
     * @param page Page number
     * @param size Page size
     * @param role Optional role filter
     * @param enabled Optional enabled status filter
     * @return Page of UserDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(int page, int size, Role role, Boolean enabled) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        
        if (role != null && enabled != null) {
            users = userRepository.findByRoleAndEnabled(role, enabled, pageable);
        } else if (role != null) {
            users = userRepository.findByRole(role, pageable);
        } else if (enabled != null) {
            users = userRepository.findByEnabled(enabled, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        
        return users.map(this::mapToDTO);
    }
    
    /**
     * Checks if a user exists with the given email.
     * 
     * @param email Email address
     * @return true if user exists, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Finds user by email.
     * 
     * @param email Email address
     * @return User entity
     * @throws UserNotFoundException if user not found
     */
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
    
    /**
     * Gets the current authenticated user principal.
     * 
     * @return UserPrincipal
     * @throws RuntimeException if not authenticated
     */
    private UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new RuntimeException("User not authenticated");
        }
        return (UserPrincipal) authentication.getPrincipal();
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
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

