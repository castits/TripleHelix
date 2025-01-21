package com.triplehelix.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.ChangePassword;
import com.triplehelix.entities.User;
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
	
    /*
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
    */
    
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword passwords) {
    	
        // Set the new password
        User authenticatedUser = userService.getAuthenticatedUser();
        authenticatedUser.setUserPassword(passwordEncoder.encode(passwords.getNewPassword()));
        userService.saveUser(authenticatedUser);

        return ResponseEntity.ok("Password updated successfully");
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        
        User user = userService.getUserByEmail(email.trim().toLowerCase());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        String resetLink = "http://localhost:8080/CambioPassword.html?email=" + email;
        String subject = "Cambia la tua password su Cascina Caccia";
        String body = "Ciao " + user.getUserName() + ",\n\n"
                    + "Per reimpostare la tua password, clicca sul link sottostante:\n"
                    + resetLink + "\n\n"
                    + "Grazie per utilizzare il nostro servizio!\n\n"
                    + "Cordiali saluti,\n"
                    + "Il team di Cascina Caccia";
        
        emailService.sendEmail(email, subject, body);
        return ResponseEntity.ok("Password reset email sent");
    }
}

