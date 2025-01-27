package com.triplehelix.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.User;
import com.triplehelix.repos.UserDAO;

/**
 * This class handles the logic related to users operations
 */
@Service
public class UserService {
	
	// Logger instance to log important events and errors
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	// Dependency injection for the feedback repository
	@Autowired
	private UserDAO userDao;
	
	// Dependency injection for the password encoder
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Register a new user and add it to the database
	 * @param user - the user to be registered
	 * @return the saved user
	 * @throws IllegalArgumentException if the email is already in use
	 */
    public User register(User user) {
    	// If the email is already registered in the database, throws an exception
    	if (userDao.findUserByUserEmail(user.getUserEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already in use: " + user.getUserEmail());
		}

    	// Encrypt the user password using the BCryptPasswordEncoder
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        
        User savedUser = userDao.save(user); // Save the user to the database
        logger.info("User registered successfully: {}", savedUser.getUserEmail());
        return savedUser; // Return the saved user
    }

    /**
     * Returns a user by their email address
     * @param email - the user's email
     * @return the user found or null if no user is found
     */
    public User getUserByEmail(String email) {
        return userDao.findUserByUserEmail(email).orElse(null);
    }
    
    /**
     * Returns a user by their password reset token
     * @param token - the password reset token
     * @return the user found or null if no user is found
     */
    public User getUserByResetToken(String token) {
        return userDao.findByResetToken(token).orElse(null);
    }

    /**
     * Returns the currently authenticated user from the security context
     * @return the authenticated user or null if no user is authenticated
     */
    public User getAuthenticatedUser() {
    	// Get the authentication from the security context
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	// If the authentication is null, unauthenticated, or anonymous, print a warning and return null
	    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
	    	logger.warn("No user is authenticated");
	        return null;
	    }
	    
	    return (User) auth.getPrincipal(); // Return the authenticated user
    }
    
    /**
     * Save a new user to the database
     * @param user - the user to be saved
     * @return the saved user
     */
    public User saveUser(User user) {
    	return userDao.save(user);
    }
    
    /**
     * Returns the role id of a specific user
     * @param user - a user
     * @return the role id of the user
     * @throws IllegalStateException if the role id is not found
     */
    public Integer getUserRole(User user) {
    	// Search the role id of the user passed as parameter
    	Integer roleId = userDao.findRoleIdByUserId(user.getUserId());
    	
    	// If the role id is not found, print an error and throw a IllegalStateException
    	if (roleId == null) {
			logger.error("Role id not found for user {}", user.getUserEmail());
			throw new IllegalStateException("Role id not found for user " + user.getUserEmail());
		}
    	
    	return roleId; // Return the role id of the user
    }

}
