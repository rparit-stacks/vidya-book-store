package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing an in-app notification for a user.
 * 
 * <p>Notifications inform users about various events such as inquiry responses,
 * new chat messages, or system announcements. Notifications can be related to
 * other entities (inquiries, conversations, products, users) for context.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link User} - User who receives the notification</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    
    /**
     * Unique identifier for the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User who receives the notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Type of notification.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    
    /**
     * Notification title.
     */
    @Column(nullable = false)
    private String title;
    
    /**
     * Notification message content.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    /**
     * Whether the notification has been read.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean read = false;
    
    /**
     * ID of the related entity (e.g., inquiry ID, conversation ID).
     */
    @Column(name = "related_entity_id")
    private Long relatedEntityId;
    
    /**
     * Type of the related entity.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "related_entity_type")
    private RelatedEntityType relatedEntityType;
    
    /**
     * Timestamp when the notification was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the notification was read (null if not read).
     */
    @Column(name = "read_at")
    private LocalDateTime readAt;
}

