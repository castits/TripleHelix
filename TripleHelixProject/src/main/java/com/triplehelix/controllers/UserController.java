package com.triplehelix.controllers;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.User;
import com.triplehelix.exceptions.UserNotFoundException;
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

import jakarta.mail.MessagingException;

/**
 * This controller handles users operations
 */
@RestController
@RequestMapping("/api/users") // Default endpoint for users operations
public class UserController {

	// Logger instance to log important events and errors
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Dependency injection for the user service
    @Autowired
    private UserService userService;

    // Dependency injection for the password encoder
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	// Dependency injection for the email service
    @Autowired
    private EmailService emailService;

    /**
     * Endpoint to handel forgotten password requests
     * @param request - the info for the password change in the body request
     * @return a ResponseEntity that indicates if the password change email has been sent
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // Get the email from the body request
        // Return a BAD REQUEST response if 'email' is not present
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        
        // Find the user associated with the email
        User user = userService.getUserByEmail(email.trim().toLowerCase());
        // Return a NOT FOUND response if the user is not present in the database
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        // Generate a unique reset token and associate it with the user
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userService.saveUser(user);

        // Prepare the email
        String cid = "bannerImage";
        String imagePath = "src/main/resources/static/assets/img/deck.jpg";
        String resetLink = "http://localhost:8080/changePassword.html?token=" + token;
        String subject = "Cambia la tua password su Cascina Caccia";
        
        try {
            String body = "<head>"
                    + "  <style>"
                    + "    body { font-family: Arial, sans-serif; color: #333333; margin: 0; padding: 0; }"
                    + "    .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; text-align: center; }"
                    + "    h1 { color: #ff8400; font-size: 24px; text-align: center; }"
                    + "    p { line-height: 1.6; font-size: 16px; text-align: left; }"
                    + "    .footer { margin-top: 20px; font-size: 14px; color: #777777; text-align: center; }"
                    + "    .button { display: inline-block; padding: 10px 20px; color: #ffffff !important; background-color: #ff8400; text-decoration: none; border-radius: 5px; font-weight: bold; }"
                    + "    .banner { display: block; max-width: 100%; height: auto; margin: 20px auto 0; border-radius: 8px; }"
                    + "  </style>"
                    + "</head>"
                    + "<body>"
                    + "  <div class='email-container'>"
                    + "    <h1>Reimposta la tua password</h1>"
                    + "    <p>Ciao <strong>" + user.getUserName() + "</strong>,</p>"
                    + "    <p>Per reimpostare la tua password, clicca sul pulsante sottostante:</p>"
                    + "    <p style='text-align: center;'><a href='" + resetLink + "' class='button'>Reimposta Password</a></p>"
                    + "    <p>Se non hai richiesto la reimpostazione della password, ignora questa email.</p>"
                    + "    <div class='footer'>"
                    + "      <p>Grazie per utilizzare il nostro servizio!</p>"
                    + "      <p><strong>Il Team di Cascina Caccia</strong></p>"
                    + "      <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />"
                    + "    </div>"
                    + "  </div>"
                    + "</body>"
                    + "</html>";

            // Send the email for password change to the user
            emailService.sendEmail(email, subject, body, imagePath, cid);

        } catch (MessagingException e) {
            System.err.println("Failed to send reminder email to " + email); // Print an error if the email can't be sent
        }
        return ResponseEntity.ok("Password reset email sent"); // Return an OK response if the email has been sent
    }

    /**
     * Endpoint to handle password reset requests
     * @param request - contains the user token and a new password in the body request
     * @return a ResponseEntity that indicates if the password change went well
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token"); // Get the token from the body request
        String newPassword = request.get("newPassword"); // Get the new password from the body request

        // Return a BAD REQUEST response if the token or the new password are not valid
        if (token == null || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token and new password are required");
        }

        // Get the user associated with the token
        User user = userService.getUserByResetToken(token);
        
        // Return NOT_FOUND response if the token is invalid
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }

        // Update the user's password and clear the reset token
        user.setUserPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userService.saveUser(user);

        return ResponseEntity.ok("Password updated successfully"); // Return an OK response if the password change went well
    }

    /**
     * Endpoint to get the role id of the authenticated user
     * @return a ResponseEntity that contains the role id of the authenticated user
     */
    @GetMapping("/auth-role")
    public ResponseEntity<Integer> getAuthenticatedUserRole() {
    	// Get the authenticated user
        User authenticatedUser = userService.getAuthenticatedUser();
        
        // Return an UNAUTHORIZED response if no user is logged
        if (authenticatedUser == null) {
            logger.warn("No authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Get the role of the authenticated user
        Integer roleId = userService.getUserRole(authenticatedUser);
        logger.info("Role id: {} for user: {}", roleId, authenticatedUser.getUserEmail()); // Print the role id
        return ResponseEntity.ok(roleId); // Return an OK response that contains the user's role id
    }
    
    /**
     * Endpoint to delete a user by id
     * @param id - the user id
     * @return a ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookingById(@PathVariable int id) {
        try {
            userService.deleteUserById(id); // Delete the user
            return ResponseEntity.ok("User deleted successfully"); // Return an OK response
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return a NOT FOUND response if there is no user with that id
        }
    }
    
    /**
     * Endpoint to create and add to the database a new admin
     * @param user - the new user in the body request
     * @return the new user
     * 
     * Only accessible to users with the ADMIN role
     */
    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")    
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
    	// If the user is already present in the database
	    if (userService.getUserByEmail(user.getUserEmail()) != null) {
	    	// Print a warning and return a CONFLICT response
	        logger.warn("Email already in use: {}" + user.getUserEmail());
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
	    }
	    
	    User savedUser = userService.createAdmin(user); // Save the new user in the database
	    return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Return a CREATED response that contains the new admin
    }

}
