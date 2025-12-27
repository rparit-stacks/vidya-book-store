package com.rps.bookstore.entity;

/**
 * Enumeration representing types of notifications.
 * 
 * <p>Defines the different types of notifications that can be sent to users:
 * <ul>
 *   <li>{@code INQUIRY_RESPONSE} - Notification when a store owner responds to an inquiry</li>
 *   <li>{@code CHAT_MESSAGE} - Notification when a new chat message is received</li>
 *   <li>{@code INQUIRY_CREATED} - Notification when a new inquiry is created</li>
 *   <li>{@code SYSTEM} - System-wide announcements or general notifications</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public enum NotificationType {
    /**
     * Inquiry response notification - sent when a store owner responds to a customer inquiry.
     */
    INQUIRY_RESPONSE,
    
    /**
     * Chat message notification - sent when a user receives a new chat message.
     */
    CHAT_MESSAGE,
    
    /**
     * Inquiry created notification - sent when a new inquiry is created (e.g., to store owners).
     */
    INQUIRY_CREATED,
    
    /**
     * System notification - general system announcements or important updates.
     */
    SYSTEM
}

