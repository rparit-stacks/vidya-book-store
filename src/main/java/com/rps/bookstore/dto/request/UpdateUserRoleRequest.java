package com.rps.bookstore.dto.request;

import com.rps.bookstore.entity.Role;
import lombok.Data;

/**
 * Request DTO for updating user role.
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Data
public class UpdateUserRoleRequest {
    
    private Role role;
}

