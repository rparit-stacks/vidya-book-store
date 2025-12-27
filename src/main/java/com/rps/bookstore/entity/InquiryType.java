package com.rps.bookstore.entity;

/**
 * Enumeration representing types of customer inquiries.
 * 
 * <p>Defines the different types of inquiries that customers can create:
 * <ul>
 *   <li>{@code BOOK_AVAILABILITY} - Inquiry about book availability and stock</li>
 *   <li>{@code GENERAL_QUESTION} - General questions about products or services</li>
 *   <li>{@code CUSTOM_ORDER} - Request for custom book orders</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public enum InquiryType {
    /**
     * Book availability inquiry - customers asking about book stock availability.
     */
    BOOK_AVAILABILITY,
    
    /**
     * General question - general questions about products, services, or policies.
     */
    GENERAL_QUESTION,
    
    /**
     * Custom order - request for custom book orders or special arrangements.
     */
    CUSTOM_ORDER
}

