package com.rps.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a product (book) in the bookstore catalog.
 * 
 * <p>This entity stores all information about a book including details like ISBN,
 * author, publisher, price, and categorization. Products can be featured or marked
 * as bestsellers. Products are organized by category and can be filtered by board
 * and class for educational books.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link Category} - Product belongs to a category</li>
 *   <li>One-to-Many with {@link Inquiry} - Products can be referenced in inquiries</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Product name (book title).
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Product description.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * ISBN number (unique identifier for books).
     */
    @Column(unique = true, length = 50)
    private String isbn;
    
    /**
     * Author name.
     */
    @Column(length = 255)
    private String author;
    
    /**
     * Publisher name.
     */
    @Column(length = 255)
    private String publisher;
    
    /**
     * Product price.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    /**
     * Category this product belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    /**
     * Educational board (SSC, HSC, ICSE, CBSE).
     */
    @Column(length = 50)
    private String board;
    
    /**
     * Class/grade level.
     */
    @Column(name = "class", length = 50)
    private String className;
    
    /**
     * Whether this product is featured.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;
    
    /**
     * Whether this product is a bestseller.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean bestseller = false;
    
    /**
     * URL to the product image.
     */
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    /**
     * List of inquiries related to this product.
     */
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<Inquiry> inquiries = new ArrayList<>();
    
    /**
     * Timestamp when the product was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the product was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

