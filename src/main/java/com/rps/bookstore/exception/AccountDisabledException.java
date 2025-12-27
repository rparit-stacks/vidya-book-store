package com.rps.bookstore.exception;

/**
 * Exception thrown when an account is disabled.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
public class AccountDisabledException extends RuntimeException {
    
    public AccountDisabledException(String message) {
        super(message);
    }
    
    public AccountDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}

