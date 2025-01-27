package com.triplehelix.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triplehelix.entities.Feedback;
import com.triplehelix.exceptions.FeedbackNotFoundException;
import com.triplehelix.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This controller handles feedbacks operations
 */
@RestController
@RequestMapping("/api/feedbacks") // Default endpoint for feedbacks operations
public class FeedbackController {

	// Dependency injection for the feedback service
    @Autowired
    private FeedbackService feedbackService;
    
    /**
     * Endpoint that add a new feedback to the database
     * @param feedbackSent - the feedback sent from an anonymous user to be added
     * @return a ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<String> addFeedback(@RequestParam Map<String, Object> feedbackSent) {
    	// Extract the raw request data from the feedback sent
    	String rawRequest = (String) feedbackSent.get("rawRequest");
        ObjectMapper objectMapper = new ObjectMapper(); // Declate an ObjectMapper to parse JSON
        
        try {
        	// Parse the raw request into a Map for easier access to fields
			Map<String, Object> parsedData = objectMapper.readValue(rawRequest, Map.class);
			
			Feedback feedback = new Feedback(); // Create a new feedback
			
			// Populate the feedback
	    	feedback.setWhichLab((String) parsedData.get("q9_whichLab"));
	    	feedback.setFormative(Integer.parseInt((String) parsedData.get("q23_formative")));
	    	feedback.setEngaging(Integer.parseInt((String) parsedData.get("q25_engaging")));
	    	feedback.setStaffQuality(Integer.parseInt((String) parsedData.get("q24_staffQuality")));
	    	feedback.setRecommendLab((String) parsedData.get("q30_recommendLab"));
	    	if (parsedData.get("q19_advices") != "") {
	    		feedback.setAdvices((String) parsedData.get("q19_advices"));
			} else {
				feedback.setAdvices(null);
			}
	    	feedback.setDate(LocalDateTime.now());
	    	
	    	feedbackService.saveFeedback(feedback); // Save the new feedback
        } catch (JsonProcessingException e) {
			e.printStackTrace();
		}

        return ResponseEntity.ok("Feedback saved"); // Return an OK response
    }
    
    /**
     * Endpoint to get all the feedbacks
     * @return ResponseEntity that contains a list of all feedbacks
     * 
     * Only accessible to users with the ADMIN role
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
    	// Get a list of all feedbacks
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks); // Return an OK response that contains a list with all feedbacks
    }
    
    /**
     * Endpoint that returns a feedback by id
     * @param id - the feedback id
     * @return a ResponseEntity that contains the feedback found
     * 
     * Only accessible to users with the ADMIN role
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getFeedbackById(@PathVariable int id) {
    	try {
            Feedback feedback = feedbackService.getFeedbackById(id); // Get the feedback by id
            return ResponseEntity.ok(feedback); // Return an OK response that contains the feedback found
        } catch (FeedbackNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return a NOT FOUND response if no feedback has been found
        }
    }

    /**
     * Endpoint that deletes a feedback by id
     * @param id - the feedback id
     * @return a ResponseEntity
     * 
     * Only accessible to users with the ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFeedback(@PathVariable int id) {
    	try {
            feedbackService.deleteFeedback(id); // Delete the feedback by id
            return ResponseEntity.ok("Feedback deleted successfully."); // Return an OK response
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return a NOT FOUND response if no feedback has been found
        }
    }

}
