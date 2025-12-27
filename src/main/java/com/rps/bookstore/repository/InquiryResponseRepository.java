package com.rps.bookstore.repository;

import com.rps.bookstore.entity.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing InquiryResponse entities.
 * 
 * <p>This repository provides standard CRUD operations for InquiryResponse entities
 * including owner responses to customer inquiries, response management, and
 * inquiry-response relationship queries.
 * 
 * <p>Supports:
 * <ul>
 *   <li>InquiryResponse CRUD operations</li>
 *   <li>Response creation and management</li>
 *   <li>Inquiry-based response queries</li>
 *   <li>User-based response queries</li>
 *   <li>Response history tracking</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {
}

