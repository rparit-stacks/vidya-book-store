package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Role;
import com.rps.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities.
 * 
 * <p>This repository provides standard CRUD operations for User entities
 * including user account management, authentication, and user profile operations.
 * 
 * <p>Supports:
 * <ul>
 *   <li>User registration and account management</li>
 *   <li>User authentication and authorization</li>
 *   <li>User profile operations</li>
 *   <li>Role-based user queries</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by email address.
     * 
     * @param email the email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    java.util.Optional<User> findByEmail(String email);
    
    /**
     * Checks if a user exists with the given email address.
     * 
     * @param email the email address to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Finds users by role with pagination.
     * 
     * @param role the role to filter by
     * @param pageable pagination information
     * @return Page of users
     */
    Page<User> findByRole(Role role, Pageable pageable);
    
    /**
     * Finds users by enabled status with pagination.
     * 
     * @param enabled the enabled status to filter by
     * @param pageable pagination information
     * @return Page of users
     */
    Page<User> findByEnabled(Boolean enabled, Pageable pageable);
    
    /**
     * Finds users by role and enabled status with pagination.
     * 
     * @param role the role to filter by
     * @param enabled the enabled status to filter by
     * @param pageable pagination information
     * @return Page of users
     */
    Page<User> findByRoleAndEnabled(Role role, Boolean enabled, Pageable pageable);
}

