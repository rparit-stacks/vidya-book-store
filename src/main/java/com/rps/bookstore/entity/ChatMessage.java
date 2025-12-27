package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a chat message in a conversation.
 * 
 * <p>Chat messages are sent between customers and the owner in conversations.
 * Each message tracks its read status and is linked to both the conversation
 * and the sender (user).
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link Conversation} - Conversation this message belongs to</li>
 *   <li>Many-to-One with {@link User} - User who sent the message</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    
    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Conversation this message belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
    
    /**
     * User who sent the message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    
    /**
     * Message content.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    /**
     * Whether the message has been read by the recipient.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean read = false;
    
    /**
     * Timestamp when the message was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

