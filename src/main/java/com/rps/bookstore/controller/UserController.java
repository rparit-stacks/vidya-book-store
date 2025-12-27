package com.rps.bookstore.controller;

import com.rps.bookstore.dto.request.UpdateProfileRequest;
import com.rps.bookstore.dto.request.UpdateUserRoleRequest;
import com.rps.bookstore.dto.response.UserDTO;
import com.rps.bookstore.entity.Role;
import com.rps.bookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user management endpoints.
 * 
 * <p>This controller handles user-related operations including:
 * <ul>
 *   <li>User profile management (get/update current user profile)</li>
 *   <li>User administration (get all users, get user by ID, update user role)</li>
 * </ul>
 * 
 * <p>Most endpoints require authentication, and some require OWNER role.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * Gets the current authenticated user's profile.
     * 
     * <p>Endpoint: GET /api/users/profile
     * 
     * @return ResponseEntity with UserDTO of current user
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        return ResponseEntity.ok(userDTO);
    }
    
    /**
     * Updates the current authenticated user's profile.
     * 
     * <p>Endpoint: PUT /api/users/profile
     * <p>Note: Email cannot be changed
     * 
     * @param request Update profile request
     * @return ResponseEntity with updated UserDTO
     */
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserDTO userDTO = userService.updateProfile(request);
        return ResponseEntity.ok(userDTO);
    }
    
    /**
     * Gets user details by ID.
     * 
     * <p>Endpoint: GET /api/users/{id}
     * <p>Requires OWNER role
     * 
     * @param id User ID
     * @return ResponseEntity with UserDTO
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    
    /**
     * Updates user role.
     * 
     * <p>Endpoint: PUT /api/users/{id}/role
     * <p>Requires OWNER role
     * 
     * @param id User ID
     * @param request Update role request
     * @return ResponseEntity with updated UserDTO
     */
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<UserDTO> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequest request) {
        UserDTO userDTO = userService.updateUserRole(id, request);
        return ResponseEntity.ok(userDTO);
    }
    
    /**
     * Gets paginated list of all users with optional filters.
     * 
     * <p>Endpoint: GET /api/users
     * <p>Requires OWNER role
     * <p>Query Parameters:
     * <ul>
     *   <li>page - Page number (default: 0)</li>
     *   <li>size - Page size (default: 20)</li>
     *   <li>role - Optional role filter (CUSTOMER, OWNER, ADMIN)</li>
     *   <li>enabled - Optional enabled status filter (true/false)</li>
     * </ul>
     * 
     * @param page Page number (0-indexed)
     * @param size Page size
     * @param role Optional role filter
     * @param enabled Optional enabled status filter
     * @return ResponseEntity with Page of UserDTO
     */
    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean enabled) {
        Page<UserDTO> users = userService.getAllUsers(page, size, role, enabled);
        return ResponseEntity.ok(users);
    }
}

