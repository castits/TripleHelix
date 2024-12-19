package com.triplehelix.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.User;
import com.triplehelix.repos.UserDAO;

@Service
public class UserService {
	
	private UserDAO userDao;
	private BCryptPasswordEncoder passwordEncoder;
	
    public User register(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userDao.save(user);
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email).orElse(null);
    }

}
