package com.rps.bookstore.service;

import com.rps.bookstore.dto.request.CreateCategoryRequest;
import com.rps.bookstore.dto.request.UpdateCategoryRequest;
import com.rps.bookstore.dto.response.CategoryDTO;
import com.rps.bookstore.dto.response.CategoryTreeDTO;
import com.rps.bookstore.entity.Category;

import java.util.List;

/**
 * Service interface for category management operations.
 * 
 * <p>This service handles all category-related operations including:
 * <ul>
 *   <li>Category CRUD operations</li>
 *   <li>Hierarchical category management</li>
 *   <li>Category tree structure retrieval</li>
 *   <li>Category validation</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public interface CategoryService {
    
    /**
     * Gets all categories.
     * 
     * @param includeSubcategories Whether to include subcategories in the response
     * @return List of CategoryDTO
     */
    List<CategoryDTO> getAllCategories(boolean includeSubcategories);
    
    /**
     * Gets category by ID with subcategories.
     * 
     * @param id Category ID
     * @return CategoryDTO
     */
    CategoryDTO getCategoryById(Long id);
    
    /**
     * Gets complete category tree structure.
     * 
     * @return List of CategoryTreeDTO representing root categories and their nested subcategories
     */
    List<CategoryTreeDTO> getCategoryTree();
    
    /**
     * Creates a new category.
     * 
     * @param request Create category request
     * @return Created CategoryDTO
     */
    CategoryDTO createCategory(CreateCategoryRequest request);
    
    /**
     * Updates an existing category.
     * 
     * @param id Category ID
     * @param request Update category request
     * @return Updated CategoryDTO
     */
    CategoryDTO updateCategory(Long id, UpdateCategoryRequest request);
    
    /**
     * Deletes a category.
     * 
     * @param id Category ID
     */
    void deleteCategory(Long id);
    
    /**
     * Gets category path as string (e.g., "School Books > SSC > Class 10").
     * 
     * @param categoryId Category ID
     * @return Category path string
     */
    String getCategoryPath(Long categoryId);
    
    /**
     * Checks if category exists.
     * 
     * @param id Category ID
     * @return true if category exists, false otherwise
     */
    boolean existsById(Long id);
    
    /**
     * Finds category by ID (entity).
     * 
     * @param id Category ID
     * @return Category entity
     */
    Category findById(Long id);
    
    /**
     * Validates that category exists.
     * 
     * @param categoryId Category ID
     * @throws CategoryNotFoundException if category not found
     */
    void validateCategoryExists(Long categoryId);
}

