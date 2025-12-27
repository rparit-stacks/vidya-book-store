package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Conversation entities.
 * 
 * <p>This repository provides standard CRUD operations for Conversation entities
 * including chat conversation management, customer-owner conversation tracking,
 * and conversation-related queries.
 * 
 * <p>Supports:
 * <ul>
 *   <li>Conversation CRUD operations</li>
 *   <li>Conversation creation and management</li>
 *   <li>Customer-based conversation queries</li>
 *   <li>Owner-based conversation queries</li>
 *   <li>Conversation lookup by customer-owner pair</li>
 *   <li>Last message timestamp tracking</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}

