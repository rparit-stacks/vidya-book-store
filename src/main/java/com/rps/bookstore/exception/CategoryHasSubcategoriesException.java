package com.rps.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to delete a category that has subcategories.
 * This exception maps to an HTTP 409 Conflict status.
 *
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryHasSubcategoriesException extends RuntimeException {
    
    public CategoryHasSubcategoriesException(String message) {
        super(message);
    }
    
    public CategoryHasSubcategoriesException(String message, Throwable cause) {
        super(message, cause);
    }
}

