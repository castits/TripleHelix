package com.triplehelix.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User findUserByEmail(String email) {
        return userDao.findUserByUserEmail(email).orElse(null);
    }

}
