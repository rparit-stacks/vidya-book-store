package com.rps.bookstore.entity;

/**
 * Enumeration representing the status of customer inquiries.
 * 
 * <p>Defines the lifecycle states of an inquiry:
 * <ul>
 *   <li>{@code PENDING} - Newly created inquiry awaiting owner response</li>
 *   <li>{@code IN_PROGRESS} - Owner has responded, inquiry is being processed</li>
 *   <li>{@code RESOLVED} - Inquiry has been resolved</li>
 *   <li>{@code CLOSED} - Inquiry has been closed (final state)</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public enum InquiryStatus {
    /**
     * Pending status - newly created inquiry awaiting owner response.
     */
    PENDING,
    
    /**
     * In progress status - owner has responded and inquiry is being processed.
     */
    IN_PROGRESS,
    
    /**
     * Resolved status - inquiry has been resolved successfully.
     */
    RESOLVED,
    
    /**
     * Closed status - inquiry has been closed (final state).
     */
    CLOSED
}

