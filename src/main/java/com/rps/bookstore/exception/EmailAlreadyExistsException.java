package com.rps.bookstore.exception;

/**
 * Exception thrown when attempting to register with an email that already exists.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

