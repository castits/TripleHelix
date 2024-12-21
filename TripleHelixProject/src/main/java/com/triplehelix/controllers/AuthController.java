package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.User;
import com.triplehelix.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Public endpoint works!");
    }
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		System.out.println("Register endpoint called with email: " + user.getUserEmail());
		if (userService.findUserByEmail(user.getUserEmail()) != null) {
			System.out.println("Email already in use: " + user.getUserEmail());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
		}
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		userService.register(user);
		System.out.println("User registered successfully: " + user.getUserEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		User existingUser = userService.findUserByEmail(user.getUserEmail());
		if (existingUser == null || !passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login not correct");
		}
		return ResponseEntity.ok("Login successful");
	}
	

}
