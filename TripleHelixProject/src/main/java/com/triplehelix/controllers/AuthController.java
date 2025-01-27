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
import com.triplehelix.exceptions.UserNotFoundException;
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pub/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
	    logger.info("Register attempt with email: {}", user.getUserEmail());

	    if (userService.getUserByEmail(user.getUserEmail()) != null) {
	        logger.warn("Email already in use: {}" + user.getUserEmail());
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use!");
	    }

	    try {
	        Role role = new Role();
	        role.setRoleId(2);
	        user.setRole(role);
	        userService.register(user);

	        logger.info("User registered successfully: {}", user.getUserEmail());

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
	        
	        emailService.sendEmail(user.getUserEmail(), subject, body, imagePath, cid);

	        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
	    } catch (Exception e) {
	        logger.error("Error during registration: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
	    }
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request) {
		logger.info("Login attempt with email: {}", user.getUserEmail());
		
		User authenticatedUser = userService.getAuthenticatedUser();

		if (authenticatedUser != null) {
            logger.warn("User is already logged in");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already logged in");
		}

		try {
			User existingUser = userService.getUserByEmail(user.getUserEmail());
						
			if (!passwordEncoder.matches(user.getUserPassword(), existingUser.getUserPassword())) {
				logger.warn("Incorrect password for {}", user.getUserEmail());
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
			}
				
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
					existingUser,
					null,
					List.of(new SimpleGrantedAuthority(existingUser.getRole().getRoleName()))
					);
			SecurityContextHolder.getContext().setAuthentication(newAuth);			
			request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			
			logger.info("Login successful for email: {}", user.getUserEmail());
			return ResponseEntity.ok("Login successful");
			
		} catch (Exception e) {
			logger.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found or incorrect credentials");
		}

	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            logger.warn("No authenticated user found");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
	    }
	    
	    new SecurityContextLogoutHandler().logout(request, response, auth);
	    request.getSession().invalidate();
	    
	    logger.info("Logout successful for user: {}", auth.getName());
	    return ResponseEntity.ok("Logout successful");
	}
	
	@GetMapping("/status")
	public ResponseEntity<String> checkStatus() {
		User authenticatedUser = userService.getAuthenticatedUser();
		
		if (authenticatedUser == null) {
			logger.warn("No authenticated user found");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
		}
		
		logger.info("Status check for user: {}", authenticatedUser.getUserEmail());
		return ResponseEntity.ok("User is logged in: " + authenticatedUser.getUserEmail());
	}
	
	@GetMapping("/is-logged")
	public ResponseEntity<Boolean> isUserLogged() {
		User authenticatedUser = userService.getAuthenticatedUser();
		boolean isLogged = authenticatedUser != null;
		
		logger.info("is user logged? {}", isLogged);
		return ResponseEntity.ok(isLogged);
	}
	
	@GetMapping("/user-info")
	public ResponseEntity<User> getLoggedUserInfo() {
		User authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            logger.warn("User is not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Authenticated user info for: {}", authenticatedUser.getUserEmail());
        return ResponseEntity.ok(authenticatedUser);
	}

}
