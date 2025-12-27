package com.rps.bookstore.security;

import com.rps.bookstore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security UserDetails implementation for authentication and authorization.
 * 
 * <p>This class wraps the User entity and implements Spring Security's UserDetails
 * interface to provide user information for authentication and authorization.
 * 
 * <p>Features:
 * <ul>
 *   <li>Encapsulates user entity for Spring Security</li>
 *   <li>Provides user authorities based on role</li>
 *   <li>Handles account status checks (enabled, non-expired, etc.)</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    
    private User user;
    
    /**
     * Factory method to create UserPrincipal from User entity.
     * 
     * @param user User entity
     * @return UserPrincipal instance
     */
    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }
    
    /**
     * Returns the authorities granted to the user.
     * 
     * @return Collection of authorities (roles)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    
    /**
     * Returns the password used to authenticate the user.
     * 
     * @return the password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    /**
     * Returns the username used to authenticate the user.
     * 
     * @return the username (email)
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }
    
    /**
     * Indicates whether the user's account has expired.
     * 
     * @return true if the user's account is valid (non-expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * Indicates whether the user is locked or unlocked.
     * 
     * @return true if the user is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    /**
     * Indicates whether the user's credentials (password) has expired.
     * 
     * @return true if the user's credentials are valid (non-expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * Indicates whether the user is enabled or disabled.
     * 
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
    
    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public Long getId() {
        return user.getId();
    }
    
    /**
     * Gets the user role.
     * 
     * @return the user role
     */
    public com.rps.bookstore.entity.Role getRole() {
        return user.getRole();
    }
}

