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
import com.triplehelix.exceptions.UserNotFoundException;
import com.triplehelix.repos.UserDAO;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    public User register(User user) {
    	if (userDao.findUserByUserEmail(user.getUserEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already in use: " + user.getUserEmail());
		}
    	
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        User savedUser = userDao.save(user);
        logger.info("User registered successfully: {}", savedUser.getUserEmail());
        return savedUser;
    }

    public User getUserByEmail(String email) {
        return userDao.findUserByUserEmail(email)
        	.orElseThrow(() -> new UserNotFoundException("User not found with email " + email));
    }
    
    public User getAuthenticatedUser() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
	    	logger.warn("No user is authenticated");
	        return null;
	    }
	    
	    return (User) auth.getPrincipal();
    }
    
    public User saveUser(User user) {
    	return userDao.save(user);
    }

}
