package com.rps.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request DTO for updating a category.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
public class UpdateCategoryRequest {
    
    /**
     * Category name.
     */
    @NotBlank(message = "Category name is required")
    private String name;
    
    /**
     * Category description.
     */
    private String description;
    
    /**
     * Parent category ID (null for root categories).
     */
    private Long parentCategoryId;
}

