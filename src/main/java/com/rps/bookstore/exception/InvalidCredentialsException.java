package com.rps.bookstore.exception;

/**
 * Exception thrown when authentication credentials are invalid.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}

