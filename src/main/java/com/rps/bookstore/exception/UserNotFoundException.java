package com.rps.bookstore.exception;

/**
 * Exception thrown when a user is not found.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

