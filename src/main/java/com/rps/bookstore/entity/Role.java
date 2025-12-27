package com.rps.bookstore.entity;

/**
 * Enumeration representing user roles in the system.
 * 
 * <p>Defines the different roles that users can have:
 * <ul>
 *   <li>{@code CUSTOMER} - Regular customer users who can browse products, 
 *       create inquiries, and chat with the owner</li>
 *   <li>{@code OWNER} - Store owner with full access to manage products, 
 *       categories, inquiries, and user management</li>
 *   <li>{@code ADMIN} - System administrator with all permissions (future use)</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public enum Role {
    /**
     * Customer role - default role for registered users.
     * Customers can browse products, create inquiries, and chat with the owner.
     */
    CUSTOMER,
    
    /**
     * Owner role - full access to manage the bookstore.
     * Owners can manage products, categories, respond to inquiries, and manage users.
     */
    OWNER,
    
    /**
     * Admin role - system administrator with all permissions.
     * Reserved for future system administration features.
     */
    ADMIN
}

