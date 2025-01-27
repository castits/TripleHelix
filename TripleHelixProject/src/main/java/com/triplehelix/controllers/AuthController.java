package com.triplehelix.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This controller handles authentication operations (registration, login, logout, and session management)
 */
@RestController
@RequestMapping("/pub/auth") // Default endpoint for authentication operations
public class AuthController {
	
	// Logger instance to log important events and errors
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
	 * Endpoint for user registration
	 * @param user - the user to be registered passed into the body of the request
	 * @return a ResponseEntity that indicates if the registration went well
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
	    logger.info("Register attempt with email: {}", user.getUserEmail());

	    // If the user is already present in the database
	    if (userService.getUserByEmail(user.getUserEmail()) != null) {
	    	// Print a warning and return a CONFLICT response
	        logger.warn("Email already in use: {}" + user.getUserEmail());
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
	    }

	    try {
	        Role role = new Role(); // Create a role for the user
	        role.setRoleId(2); // Assign the role with id = 2 (USER) as default
	        user.setRole(role); // Set the role to the user
	        
	        userService.register(user); // Add the user to the database

	        logger.info("User registered successfully: {}", user.getUserEmail());

	        // Prepare a welcome email
	        String cid = "bannerImage";
	        String imagePath = "src/main/resources/static/assets/img/deck.jpg";
	        String subject = "Benvenuto su Cascina Caccia";
	        
	        String body = "<head>"
	                + "  <style>"
	                + "    body { font-family: Arial, sans-serif; color: #333333; margin: 0; padding: 0; }"
	                + "    .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; text-align: center; }"
	                + "    h1 { color: #ff8400; font-size: 24px; text-align: center; }"
	                + "    p { line-height: 1.6; font-size: 16px; text-align: left; }"
	                + "    .footer { margin-top: 20px; font-size: 14px; color: #9B9B9B; text-align: center; }"
	                + "    .button { display: inline-block; padding: 10px 20px; color: #ffffff !important; background-color: #ff8400; text-decoration: none; border-radius: 5px; font-weight: bold; }"
	                + "    .banner { display: block; max-width: 100%; height: auto; margin: 20px auto 0; border-radius: 8px; }"
	                + "  </style>"
	                + "</head>"
	                + "<body>"
	                + "  <div class='email-container'>"
	                + "    <h1>Benvenuto su Cascina Caccia!</h1>"
	                + "    <p>Ciao <strong>" + user.getUserName() + "</strong>,</p>"
	                + "    <p>Grazie per esserti registrato al nostro sito!</p>"
	                + "    <p style='text-align: center;'><a href='http://localhost:8080' class='button'>Accedi al sito</a></p>"
	                + "    <div class='footer'>"
	                + "      <p>Con i migliori saluti,</p>"
	                + "      <p><strong>Il Team di Cascina Caccia</strong></p>"
	                + "    <img src='cid:" + cid + "' alt='Banner' class='banner' />"
	                + "    </div>"
	                + "  </div>"
	                + "</body>"
	                + "</html>";
	        
	        // Send the email
	        emailService.sendEmail(user.getUserEmail(), subject, body, imagePath, cid);

	        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
	    } catch (Exception e) {
	        logger.error("Error during registration: {}", e.getMessage()); // Print an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed"); // Return an INTERNAL SERVER ERROR response
	    }
	}

	/**
	 * Endpoint for user login
	 * @param user - the login credentials passed into the body of the request
	 * @param request - the HTTP request
	 * @return a ResponseEntity that indicates if the login went well
	 */
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request) {
		logger.info("Login attempt with email: {}", user.getUserEmail());
		
		User authenticatedUser = userService.getAuthenticatedUser(); // Get the authenticated user

		// If the authenticated user is not null, it means that a user is already logged
		if (authenticatedUser != null) {
			// Print a warning and return a FORBIDDEN response
            logger.warn("User is already logged in");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already logged in");
		}

		try {
			// Search the user by email from the database
			User existingUser = userService.getUserByEmail(user.getUserEmail());
			
			// Compare the passwords
			if (!passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
				// If passwords don't match, print a warning and return a UNAUTHORIZED response
				logger.warn("Incorrect password for {}", user.getUserEmail());
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
			}
			
			// Set the authentication in the security context
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
					existingUser,
					null,
					List.of(new SimpleGrantedAuthority(existingUser.getRole().getRoleName()))
					);
			SecurityContextHolder.getContext().setAuthentication(newAuth);			
			request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			
			logger.info("Login successful for email: {}", user.getUserEmail()); // Print a successful message
			return ResponseEntity.ok("Login successful"); // Return an OK response
			
		} catch (Exception e) {
			logger.error("Login failed: {}", e.getMessage()); // Print an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found or incorrect credentials"); // Return an UNAUTHORIZED response
		}

	}

	/**
	 * Endpoint for user logout
	 * @param request - the HTTP request
	 * @param response - the HTTP response
	 * @return a ResponseEntity that indicates if the logout went well
	 */
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		// Check if a user is logged
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    // If no user is logged, print a warning and an UNAUTHORIZED response 
	    if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            logger.warn("No authenticated user found");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
	    }
	    
	    // Logout the user
	    new SecurityContextLogoutHandler().logout(request, response, auth);
	    request.getSession().invalidate();
	    
	    logger.info("Logout successful for user: {}", auth.getName()); // Print a successful message
	    return ResponseEntity.ok("Logout successful"); // Return an OK response
	}
	
	/**
	 * Endpoint to check the login status
	 * @return a ResponseEntity indicating whether a user is logged in
	 */
	@GetMapping("/status")
	public ResponseEntity<String> checkStatus() {
		User authenticatedUser = userService.getAuthenticatedUser(); // Get authenticated user
		
		// If no user is authenticated
		if (authenticatedUser == null) {
			logger.warn("No authenticated user found"); // Print a warning message
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in"); // Return an UNAUTHORIZED response
		}
		
		logger.info("Status check for user: {}", authenticatedUser.getUserEmail()); // Print a message that indicates which user is logged in
		return ResponseEntity.ok("User is logged in: " + authenticatedUser.getUserEmail()); // Return an OK response that indicates which user is logged in
	}
	
	/**
	 * Endpoint to check if a user is logged in
	 * @return a ResponseEntity that contains a boolean value
	 * (true if a user is logged in, false if no user is logged in)
	 */
	@GetMapping("/is-logged")
	public ResponseEntity<Boolean> isUserLogged() {
		User authenticatedUser = userService.getAuthenticatedUser(); // Get the authenticated user
		boolean isLogged = authenticatedUser != null; // Get a boolean value. Checks if the authenticated user is not null
		
		logger.info("is user logged? {}", isLogged); // Print the boolean value
		return ResponseEntity.ok(isLogged); // Return an OK response that contains the boolean value
	}
	
	/**
	 * Endpoint to get the authenticated user
	 * @return a ResponseEntity that contains the authenticated user
	 */
	@GetMapping("/user-info")
	public ResponseEntity<User> getLoggedUserInfo() {
		User authenticatedUser = userService.getAuthenticatedUser(); // Get the authenticated user

		// If no user is authenticated
        if (authenticatedUser == null) {
            logger.warn("User is not logged in"); // Print a warning message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return an UNAUTHORIZED response
        }

        logger.info("Authenticated user info for: {}", authenticatedUser.getUserEmail()); // Print the authenticated user email
        return ResponseEntity.ok(authenticatedUser); // Return an OK response that contains the authenticated user
	}

}
