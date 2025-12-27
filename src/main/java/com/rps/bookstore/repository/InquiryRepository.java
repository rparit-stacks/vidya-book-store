package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Inquiry entities.
 * 
 * <p>This repository provides standard CRUD operations for Inquiry entities
 * including customer inquiry management, inquiry status tracking, and inquiry
 * filtering operations.
 * 
 * <p>Supports:
 * <ul>
 *   <li>Inquiry CRUD operations</li>
 *   <li>Inquiry creation and management</li>
 *   <li>Inquiry status tracking and updates</li>
 *   <li>User-based inquiry queries</li>
 *   <li>Product-based inquiry filtering</li>
 *   <li>Status and type-based filtering</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}

