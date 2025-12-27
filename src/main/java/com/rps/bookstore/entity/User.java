package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a user in the bookstore system.
 * 
 * <p>This entity stores user account information including authentication credentials,
 * personal information, role assignments, and account status. Users can have different
 * roles (CUSTOMER, OWNER, ADMIN) which determine their access levels.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>One-to-Many with {@link Inquiry} - Users can create multiple inquiries</li>
 *   <li>One-to-Many with {@link Conversation} - Users can participate in conversations</li>
 *   <li>One-to-Many with {@link ChatMessage} - Users can send messages</li>
 *   <li>One-to-Many with {@link Notification} - Users receive notifications</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User's email address (unique, required for authentication).
     */
    @Column(unique = true, nullable = false)
    private String email;
    
    /**
     * User's phone number.
     */
    @Column(length = 20)
    private String phone;
    
    /**
     * Encrypted password (BCrypt hashed).
     */
    @Column(nullable = false)
    private String password;
    
    /**
     * User's first name.
     */
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    /**
     * User's last name.
     */
    @Column(name = "last_name", length = 100)
    private String lastName;
    
    /**
     * User's role in the system (CUSTOMER, OWNER, ADMIN).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.CUSTOMER;
    
    /**
     * Whether the user account is enabled (active).
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;
    
    /**
     * Timestamp when the user account was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the user account was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

