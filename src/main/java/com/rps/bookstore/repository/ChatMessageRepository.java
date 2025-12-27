package com.rps.bookstore.repository;

import com.rps.bookstore.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ChatMessage entities.
 * 
 * <p>This repository provides standard CRUD operations for ChatMessage entities
 * including chat message management, message persistence, read receipt tracking,
 * and message-related queries.
 * 
 * <p>Supports:
 * <ul>
 *   <li>ChatMessage CRUD operations</li>
 *   <li>Message creation and persistence</li>
 *   <li>Conversation-based message queries</li>
 *   <li>Sender-based message queries</li>
 *   <li>Read status tracking and updates</li>
 *   <li>Unread message counting</li>
 *   <li>Message pagination and ordering</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}

