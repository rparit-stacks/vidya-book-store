package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Category entities.
 * 
 * <p>This repository provides standard CRUD operations for Category entities
 * including hierarchical category management, category tree operations, and
 * category organization for products.
 * 
 * <p>Supports:
 * <ul>
 *   <li>Category CRUD operations</li>
 *   <li>Hierarchical category management (parent-child relationships)</li>
 *   <li>Category tree structure queries</li>
 *   <li>Category slug-based lookups</li>
 *   <li>Category path resolution</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Finds all root categories (categories with no parent).
     * 
     * @return List of root categories
     */
    List<Category> findByParentCategoryIsNull();
    
    /**
     * Finds all subcategories of a given parent category.
     * 
     * @param parentCategory The parent category
     * @return List of subcategories
     */
    List<Category> findByParentCategory(Category parentCategory);
    
    /**
     * Finds category by slug.
     * 
     * @param slug The category slug
     * @return Optional containing the category if found
     */
    Optional<Category> findBySlug(String slug);
    
    /**
     * Checks if a category exists with the given name and parent category.
     * 
     * @param name Category name
     * @param parentCategory Parent category (null for root categories)
     * @return true if category exists, false otherwise
     */
    boolean existsByNameAndParentCategory(String name, Category parentCategory);
}
