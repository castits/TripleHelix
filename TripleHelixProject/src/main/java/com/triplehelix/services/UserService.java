package com.triplehelix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.User;
import com.triplehelix.repos.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    public User register(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userDao.save(user);
    }

    public User getUserByEmail(String email) {
        return userDao.findUserByUserEmail(email).orElse(null);
    }
    
    public User getAuthenticatedUser() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
	        return null;
	    }
	    
	    return (User) auth.getPrincipal();
    }
    
    public User saveUser(User user) {
    	return userDao.save(user);
    }

}
