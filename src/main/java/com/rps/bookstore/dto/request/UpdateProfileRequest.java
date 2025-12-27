package com.rps.bookstore.dto.request;

import lombok.Data;

/**
 * Request DTO for updating user profile.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
public class UpdateProfileRequest {
    
    private String phone;
    private String firstName;
    private String lastName;
}

