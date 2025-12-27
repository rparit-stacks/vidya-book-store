package com.rps.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for category data.
 * This class represents a category with its subcategories and basic information.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    /**
     * Category ID.
     */
    private Long id;
    
    /**
     * Category name.
     */
    private String name;
    
    /**
     * Category description.
     */
    private String description;
    
    /**
     * Parent category ID (null for root categories).
     */
    private Long parentCategoryId;
    
    /**
     * Parent category name (null for root categories).
     */
    private String parentCategoryName;
    
    /**
     * URL-friendly slug.
     */
    private String slug;
    
    /**
     * Category level in hierarchy (0 for root categories).
     */
    private Integer level;
    
    /**
     * List of subcategories.
     */
    @Builder.Default
    private List<CategoryDTO> subcategories = new ArrayList<>();
    
    /**
     * Product count in this category.
     */
    private Long productCount;
    
    /**
     * Timestamp when category was created.
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when category was last updated.
     */
    private LocalDateTime updatedAt;
}

