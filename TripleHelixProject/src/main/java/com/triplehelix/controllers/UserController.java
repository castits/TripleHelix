package com.triplehelix.controllers;

import java.util.Map;
import java.util.UUID;

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
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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
        
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userService.saveUser(user);

        String resetLink = "http://localhost:8080/CambioPassword.html?token=" + token;
        String subject = "Cambia la tua password su Cascina Caccia";
        
        try {
        	String body = "<head>" +
                    "  <style>" +
                    "    body { font-family: Arial, sans-serif; color: #333333; }" +
                    "    .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; }" +
                    "    h1 { color: #0056b3; font-size: 24px; text-align: center; }" +
                    "    p { line-height: 1.6; font-size: 16px; }" +
                    "    .footer { margin-top: 20px; font-size: 14px; color: #777777; }" +
                    "    .button { display: inline-block; padding: 10px 20px; color: #ffffff; background-color: #0056b3; text-decoration: none; border-radius: 5px; font-weight: bold; }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <h1>Reimposta la tua password</h1>" +
                    "    <p>Ciao <strong>" + user.getUserName() + "</strong>,</p>" +
                    "    <p>Per reimpostare la tua password, clicca sul pulsante sottostante:</p>" +
                    "    <p style='text-align: center;'><a href='" + resetLink + "' class='button'>Reimposta Password</a></p>" +
                    "    <p>Se non hai richiesto la reimpostazione della password, ignora questa email.</p>" +
                    "    <div class='footer'>" +
                    "      <p>Grazie per utilizzare il nostro servizio!</p>" +
                    "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

      emailService.sendEmail(email, subject, body);

		} catch (MessagingException e) {
			System.err.println("Failed to send reminder email to " + email);
		}
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token and new password are required");
        }

        User user = userService.getUserByResetToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }

        user.setUserPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userService.saveUser(user);

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
