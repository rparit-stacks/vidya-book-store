package com.rps.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to delete a category that has associated products.
 * This exception maps to an HTTP 409 Conflict status.
 *
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryHasProductsException extends RuntimeException {
    
    public CategoryHasProductsException(String message) {
        super(message);
    }
    
    public CategoryHasProductsException(String message, Throwable cause) {
        super(message, cause);
    }
}

