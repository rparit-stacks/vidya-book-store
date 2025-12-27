package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing an owner's response to a customer inquiry.
 * 
 * <p>This entity stores responses from the store owner to customer inquiries.
 * Each response is linked to both the inquiry and the user (owner) who created it.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link Inquiry} - Inquiry this response belongs to</li>
 *   <li>Many-to-One with {@link User} - Owner who created the response</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "inquiry_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryResponse {
    
    /**
     * Unique identifier for the response.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Inquiry this response belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private Inquiry inquiry;
    
    /**
     * User (owner) who created the response.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Response message content.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    /**
     * Timestamp when the response was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

