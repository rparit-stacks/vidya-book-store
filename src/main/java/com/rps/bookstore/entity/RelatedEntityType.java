package com.rps.bookstore.entity;

/**
 * Enumeration representing types of entities that can be related to a notification.
 * 
 * <p>Defines the different entity types that a notification can reference:
 * <ul>
 *   <li>{@code INQUIRY} - Related to an Inquiry entity</li>
 *   <li>{@code CONVERSATION} - Related to a Conversation entity</li>
 *   <li>{@code PRODUCT} - Related to a Product entity</li>
 *   <li>{@code USER} - Related to a User entity</li>
 * </ul>
 * 
 * <p>Used in conjunction with {@link Notification#relatedEntityId} to provide
 * context and enable navigation to the related entity when a notification is clicked.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public enum RelatedEntityType {
    /**
     * Related entity is an Inquiry.
     */
    INQUIRY,
    
    /**
     * Related entity is a Conversation.
     */
    CONVERSATION,
    
    /**
     * Related entity is a Product.
     */
    PRODUCT,
    
    /**
     * Related entity is a User.
     */
    USER
}

