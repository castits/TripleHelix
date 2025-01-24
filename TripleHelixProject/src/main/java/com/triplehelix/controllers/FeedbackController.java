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


@RestController
@RequestMapping("/api/feedbacks") // Base URL for all feedback-related endpoints
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService; // Inject the FeedbackService
    
    @PostMapping("/add")
    public ResponseEntity<String> addFeedback(@RequestParam Map<String, Object> feedbackSent) {
    	String rawRequest = (String) feedbackSent.get("rawRequest");
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
			Map<String, Object> parsedData = objectMapper.readValue(rawRequest, Map.class);
			
			Feedback feedback = new Feedback();
			
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
	    	
	    	feedbackService.saveFeedback(feedback);
        } catch (JsonProcessingException e) {
			e.printStackTrace();
		}

        return ResponseEntity.ok("Feedback saved");
    }
    
    // Get all feedbacks
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks	);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getFeedbackById(@PathVariable int id) {
    	try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(feedback);
        } catch (FeedbackNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFeedback(@PathVariable int id) {
    	try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.ok("Feedback deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
