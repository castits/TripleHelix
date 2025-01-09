package com.triplehelix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.Role;
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
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		System.out.println("Register endpoint called with email: " + user.getUserEmail());
		
		if (userService.getUserByEmail(user.getUserEmail()) != null) {
			System.out.println("Email already in use: " + user.getUserEmail());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
		}
	    user.setUserPassword(user.getUserPassword());
	    Role role = new Role();
	    role.setRoleId(2);
	    user.setRole(role);
		userService.register(user);
	    
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request) {
		User authenticatedUser = userService.getAuthenticatedUser();

		if (authenticatedUser != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already logged in");
		}

	    User existingUser = userService.getUserByEmail(user.getUserEmail());
	    if (existingUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	    }

	    if (passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
	        Authentication newAuth = new UsernamePasswordAuthenticationToken(
	            existingUser,
	            null,
	            List.of(new SimpleGrantedAuthority(existingUser.getRole().getRoleName()))
	        );
	        SecurityContextHolder.getContext().setAuthentication(newAuth);
	        
	        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	        
	        return ResponseEntity.ok("Login successful");
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
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
		User authenticatedUser = userService.getAuthenticatedUser();
		
		if (authenticatedUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
		}
		
		return ResponseEntity.ok("User is logged in: " + authenticatedUser.getUserEmail());
	}

}
