package com.rps.bookstore.security;

import com.rps.bookstore.entity.User;
import com.rps.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom UserDetailsService implementation for Spring Security.
 * 
 * <p>This service loads user details from the database for authentication.
 * It implements Spring Security's UserDetailsService interface to provide
 * user information based on username (email).
 * 
 * <p>Responsibilities:
 * <ul>
 *   <li>Load user by email (username)</li>
 *   <li>Convert User entity to UserPrincipal</li>
 *   <li>Handle user not found exceptions</li>
 * </ul>
 * 
 * @author Bookstore Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Loads user details by username (email).
     * 
     * @param username the username (email) identifying the user
     * @return UserDetails containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        
        return UserPrincipal.create(user);
    }
    
    /**
     * Loads user details by user ID.
     * 
     * @param id the user ID
     * @return UserDetails containing user information
     * @throws UserNotFoundException if user is not found
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        
        return UserPrincipal.create(user);
    }
}

