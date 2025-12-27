package com.rps.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request DTO for creating a new category.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
public class CreateCategoryRequest {
    
    /**
     * Category name (required).
     */
    @NotBlank(message = "Category name is required")
    private String name;
    
    /**
     * Category description (optional).
     */
    private String description;
    
    /**
     * Parent category ID (optional, null for root categories).
     */
    private Long parentCategoryId;
}

