package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.RegisterRequest;
import com.rps.bookstore.dto.request.UpdateProfileRequest;
import com.rps.bookstore.dto.request.UpdateUserRoleRequest;
import com.rps.bookstore.dto.response.UserDTO;
import com.rps.bookstore.entity.Role;
import com.rps.bookstore.entity.User;
import org.springframework.data.domain.Page;

/**
 * Service interface for user management operations.
 * 
 * <p>This service handles all user-related operations including:
 * <ul>
 *   <li>User registration and account creation</li>
 *   <li>User profile management</li>
 *   <li>User role management</li>
 *   <li>User queries and listing</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public interface UserService {
    
    /**
     * Registers a new user account.
     * 
     * @param request Registration request with user details
     * @return UserDTO of created user
     */
    UserDTO registerUser(RegisterRequest request);
    
    /**
     * Gets the current authenticated user's profile.
     * 
     * @return UserDTO of current user
     */
    UserDTO getCurrentUser();
    
    /**
     * Updates the current authenticated user's profile.
     * 
     * @param request Update profile request
     * @return Updated UserDTO
     */
    UserDTO updateProfile(UpdateProfileRequest request);
    
    /**
     * Gets user details by ID.
     * 
     * @param id User ID
     * @return UserDTO
     */
    UserDTO getUserById(Long id);
    
    /**
     * Updates user role.
     * 
     * @param userId User ID
     * @param request Update role request
     * @return Updated UserDTO
     */
    UserDTO updateUserRole(Long userId, UpdateUserRoleRequest request);
    
    /**
     * Gets paginated list of all users with optional filters.
     * 
     * @param page Page number (0-indexed)
     * @param size Page size
     * @param role Optional role filter
     * @param enabled Optional enabled status filter
     * @return Page of UserDTO
     */
    Page<UserDTO> getAllUsers(int page, int size, Role role, Boolean enabled);
    
    /**
     * Checks if a user exists with the given email.
     * 
     * @param email Email address
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Finds user by email.
     * 
     * @param email Email address
     * @return User entity
     */
    User findByEmail(String email);
}
