package com.rps.bookstore.controller;

import com.rps.bookstore.dto.request.CreateCategoryRequest;
import com.rps.bookstore.dto.request.UpdateCategoryRequest;
import com.rps.bookstore.dto.response.CategoryDTO;
import com.rps.bookstore.dto.response.CategoryTreeDTO;
import com.rps.bookstore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for category management endpoints.
 * 
 * <p>This controller handles category-related operations including:
 * <ul>
 *   <li>Category listing and retrieval</li>
 *   <li>Category tree structure</li>
 *   <li>Category CRUD operations (Owner only)</li>
 * </ul>
 * 
 * <p>Most read operations are public, while create/update/delete require OWNER role.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    /**
     * Gets all categories.
     * 
     * <p>Endpoint: GET /api/categories
     * 
     * @param includeSubcategories Whether to include subcategories (default: false)
     * @return ResponseEntity with List of CategoryDTO
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @RequestParam(defaultValue = "false") boolean includeSubcategories) {
        List<CategoryDTO> categories = categoryService.getAllCategories(includeSubcategories);
        return ResponseEntity.ok(categories);
    }
    
    /**
     * Gets category by ID with subcategories.
     * 
     * <p>Endpoint: GET /api/categories/{id}
     * 
     * @param id Category ID
     * @return ResponseEntity with CategoryDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    /**
     * Gets complete category tree structure.
     * 
     * <p>Endpoint: GET /api/categories/tree
     * 
     * @return ResponseEntity with List of CategoryTreeDTO
     */
    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeDTO>> getCategoryTree() {
        List<CategoryTreeDTO> tree = categoryService.getCategoryTree();
        return ResponseEntity.ok(tree);
    }
    
    /**
     * Creates a new category.
     * 
     * <p>Endpoint: POST /api/categories
     * <p>Requires OWNER role
     * 
     * @param request Create category request
     * @return ResponseEntity with created CategoryDTO
     */
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryDTO category = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    
    /**
     * Updates an existing category.
     * 
     * <p>Endpoint: PUT /api/categories/{id}
     * <p>Requires OWNER role
     * 
     * @param id Category ID
     * @param request Update category request
     * @return ResponseEntity with updated CategoryDTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request) {
        CategoryDTO category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }
    
    /**
     * Deletes a category.
     * 
     * <p>Endpoint: DELETE /api/categories/{id}
     * <p>Requires OWNER role
     * 
     * @param id Category ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

