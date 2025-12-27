package com.rps.bookstore.exception;

/**
 * Exception thrown when a user is not authorized to access a resource.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

