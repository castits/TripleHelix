package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.User;
import com.triplehelix.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pub/auth")
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
	
	@PostMapping("/prova")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		System.out.println("User: " + user.getUserEmail());
		User existingUser = userService.findUserByEmail(user.getUserEmail());
		if (existingUser == null || !passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login not correctssss");
		}
		return ResponseEntity.ok("Login successful");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return ResponseEntity.ok("Logout successful");
	}
	
	@GetMapping("/status")
	public ResponseEntity<String> checkStatus() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
	        return ResponseEntity.ok("User is logged in: " + auth.getName());
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
	}
	
	@GetMapping("/autenticato")
	public ResponseEntity<String> provaUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
	        return ResponseEntity.ok("Prova successful - User authenticated");
	    }
	    return ResponseEntity.ok("Prova successful - User is anonymous");
	}

}
