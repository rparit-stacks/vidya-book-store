package com.rps.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for category tree structure.
 * This class represents a category in the tree structure with nested subcategories.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeDTO {
    
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
     * URL-friendly slug.
     */
    private String slug;
    
    /**
     * Category level in hierarchy (0 for root categories).
     */
    private Integer level;
    
    /**
     * List of subcategories (nested tree structure).
     */
    @Builder.Default
    private List<CategoryTreeDTO> subcategories = new ArrayList<>();
}

