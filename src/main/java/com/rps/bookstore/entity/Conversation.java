package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a chat conversation between a customer and the owner.
 * 
 * <p>Conversations facilitate real-time communication between customers and the
 * store owner. Each customer has one conversation with the owner. The conversation
 * tracks the last message timestamp and contains all chat messages.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link User} - Customer participating in the conversation</li>
 *   <li>Many-to-One with {@link User} - Owner participating in the conversation</li>
 *   <li>One-to-Many with {@link ChatMessage} - Messages in this conversation</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "conversations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "owner_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {
    
    /**
     * Unique identifier for the conversation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Customer participating in the conversation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    
    /**
     * Owner participating in the conversation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    /**
     * Timestamp of the last message in this conversation.
     */
    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;
    
    /**
     * List of messages in this conversation.
     */
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatMessage> messages = new ArrayList<>();
    
    /**
     * Timestamp when the conversation was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the conversation was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

