package com.triplehelix.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.ChangePassword;
import com.triplehelix.entities.User;
import com.triplehelix.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword passwords) {
        User authenticatedUser = userService.getAuthenticatedUser();
		
		if (authenticatedUser == null) {
			logger.warn("Unauthorized password change attempt");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
		}

		// Verify old password
        if (!passwordEncoder.matches(passwords.getOldPassword(), authenticatedUser.getUserPassword())) {
        	logger.error("Incorrect old password for user: {}", authenticatedUser.getUserEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }

        // Set the new password
        authenticatedUser.setUserPassword(passwordEncoder.encode(passwords.getNewPassword()));
        userService.saveUser(authenticatedUser);
        logger.info("Password updated successfully for user: {}", authenticatedUser.getUserEmail());
        return ResponseEntity.ok("Password updated successfully");
    }
    
    @GetMapping("/auth-role")
    public ResponseEntity<Integer> getAuthenticatedUserRole() {
    	User authenticatedUser = userService.getAuthenticatedUser();
    	
    	if (authenticatedUser == null) {
            logger.warn("No authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	Integer roleId = userService.getUserRole(authenticatedUser);
        logger.info("Role id: {} for user: {}", roleId, authenticatedUser.getUserEmail());
        return ResponseEntity.ok(roleId);
    }
    
}
