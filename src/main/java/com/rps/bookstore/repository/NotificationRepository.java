package com.rps.bookstore.repository;

import com.rps.bookstore.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Notification entities.
 * 
 * <p>This repository provides standard CRUD operations for Notification entities
 * including in-app notification management, notification delivery tracking,
 * and notification-related queries.
 * 
 * <p>Supports:
 * <ul>
 *   <li>Notification CRUD operations</li>
 *   <li>Notification creation and management</li>
 *   <li>User-based notification queries</li>
 *   <li>Notification type-based filtering</li>
 *   <li>Read status tracking and updates</li>
 *   <li>Unread notification counting</li>
 *   <li>Notification pagination and ordering</li>
 *   <li>Related entity-based queries</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

