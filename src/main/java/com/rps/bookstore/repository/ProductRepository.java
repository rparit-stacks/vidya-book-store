package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Product entities.
 * 
 * <p>This repository provides standard CRUD operations for Product entities
 * including product catalog management, product search, and product filtering
 * operations.
 * 
 * <p>Supports:
 * <ul>
 *   <li>Product catalog management (CRUD operations)</li>
 *   <li>Product search and filtering</li>
 *   <li>Category-based product queries</li>
 *   <li>Board and class-based filtering</li>
 *   <li>Featured and bestseller product queries</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Counts products by category ID.
     * 
     * @param categoryId Category ID
     * @return Count of products in the category
     */
    long countByCategoryId(Long categoryId);
}

