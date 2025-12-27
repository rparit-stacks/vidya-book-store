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
 * Entity representing a product category with hierarchical structure.
 * 
 * <p>Categories can have parent-child relationships allowing for nested category
 * structures (e.g., School Books > SSC > Class 10). Each category has a unique
 * slug for URL-friendly access.
 * 
 * <p>Relationships:
 * <ul>
 *   <li>Many-to-One with {@link Category} - Parent category relationship</li>
 *   <li>One-to-Many with {@link Category} - Subcategories</li>
 *   <li>One-to-Many with {@link Product} - Products belonging to this category</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    
    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Category name.
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Category description.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * Parent category (null for root categories).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
    
    /**
     * URL-friendly slug (unique identifier for URLs).
     */
    @Column(unique = true, nullable = false)
    private String slug;
    
    /**
     * List of subcategories (child categories).
     */
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Category> subcategories = new ArrayList<>();
    
    /**
     * List of products in this category.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Product> products = new ArrayList<>();
    
    /**
     * Timestamp when the category was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the category was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

