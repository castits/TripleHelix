package com.triplehelix.services;

import com.triplehelix.entities.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is a custom implementation of Spring Security's UserDetailsService
 * It is used to retrieve user details from the database during authentication
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Dependency on UserService to retrieve user information
	@Lazy
	@Autowired
    private final UserService userService;

	/**
	 * Constructor for dependency injection of UserService
	 * @param userService - the UserService instance
	 */
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns user's details found by user email
     * @param email - the user's email
     * @return a UserDetails that contains the user's information found
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email); // Find the user by email
        
        // If the user is not found, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        // Return the user's role and create a GrantedAuthority
        String userRole = user.getRole().getRoleName();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole); // Prefixing with "ROLE_" as per Spring Security conventions

     // Return a UserDetails object with the user's email, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUserEmail(), // The user's email
                user.getUserPassword(), // The user's password
                List.of(authority) // The list of user roles (in this case, the user will have just one role)
        );
    }
}
