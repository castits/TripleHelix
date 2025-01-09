package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.ChangePassword;
import com.triplehelix.entities.User;
import com.triplehelix.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword passwords) {
        User authenticatedUser = userService.getAuthenticatedUser();
		
		if (authenticatedUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
		}

		// Verify old password
        if (!passwordEncoder.matches(passwords.getOldPassword(), authenticatedUser.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }

        // Set the new password
        authenticatedUser.setUserPassword(passwordEncoder.encode(passwords.getNewPassword()));
        userService.saveUser(authenticatedUser);

        return ResponseEntity.ok("Password updated successfully");
    }
    
}
