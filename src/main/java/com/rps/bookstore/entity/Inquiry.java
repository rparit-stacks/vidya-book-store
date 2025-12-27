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
 * Entity representing a customer inquiry.
 * 
 * <p>Inquiries allow customers to ask questions about products, availability, or
 * make custom order requests. Each inquiry has a type, status, and can be related
 * to a specific product. Owners can respond to inquiries with InquiryResponse entities.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link User} - Customer who created the inquiry</li>
 *   <li>Many-to-One with {@link Product} - Optional product the inquiry is about</li>
 *   <li>One-to-Many with {@link InquiryResponse} - Owner responses to the inquiry</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "inquiries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {
    
    /**
     * Unique identifier for the inquiry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User (customer) who created the inquiry.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Type of inquiry (BOOK_AVAILABILITY, GENERAL_QUESTION, CUSTOM_ORDER).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryType type;
    
    /**
     * Inquiry subject/title.
     */
    @Column(nullable = false, length = 200)
    private String subject;
    
    /**
     * Inquiry message content.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    /**
     * Product this inquiry is about (optional).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    /**
     * Current status of the inquiry.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private InquiryStatus status = InquiryStatus.PENDING;
    
    /**
     * List of responses to this inquiry.
     */
    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InquiryResponse> responses = new ArrayList<>();
    
    /**
     * Timestamp when the inquiry was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the inquiry was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Timestamp when the inquiry was resolved (null if not resolved).
     */
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
}

