package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	    user.setUserPassword(user.getUserPassword());
		userService.register(user);
	    
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "Login page";
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated()) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already logged in");
	    }
	    
	    User existingUser = userService.findUserByEmail(user.getUserEmail());
	    if (existingUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	    }
	    
	    if (passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
	    	Authentication newAuth = new UsernamePasswordAuthenticationToken(
    			existingUser,
    			user.getUserPassword()
    			);
	    	SecurityContextHolder.getContext().setAuthentication(newAuth);
	    	
	    	System.out.println("User authenticated: " + existingUser.getUserEmail());
	        System.out.println("Authentication context: " + newAuth.getPrincipal());
	    	
	    	return ResponseEntity.ok("Login successful");
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
	}*/

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
	    if (auth != null) {
	        System.out.println("Authentication is authenticated: " + auth.isAuthenticated());
	        System.out.println("Authentication name: " + auth.getName());
	    } else {
	        System.out.println("Authentication is null");
	    }

	    if (auth != null && auth.isAuthenticated()) {
	        return ResponseEntity.ok("User is logged in: " + auth.getName());
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
	}


}
