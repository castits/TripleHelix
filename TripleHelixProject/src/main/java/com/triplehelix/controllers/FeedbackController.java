package com.triplehelix.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triplehelix.entities.Feedback;
import com.triplehelix.services.EmailService;
import com.triplehelix.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/feedbacks") // Base URL for all feedback-related endpoints
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService; // Inject the FeedbackService
    
    @PostMapping("/add")
    public ResponseEntity<String> addFeedback(@RequestParam Map<String, Object> feedbackSent) {
        //System.out.println(feedbackSent);
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
	    	feedback.setAdvices((String) parsedData.get("q19_advices"));
	    	feedback.setDate(LocalDateTime.now());
	    	
	    	feedbackService.saveFeedback(feedback);
        } catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
    	
        
        
        return ResponseEntity.ok("Feedback saved");
    }
    
/*
    // Get all feedbacks
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create new feedback
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }

    // Update existing feedback
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int id, @RequestBody Feedback feedbackDetails) {
        Optional<Feedback> updatedFeedback = feedbackService.updateFeedback(id, feedbackDetails);
        return updatedFeedback.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable int id) {
        boolean isDeleted = feedbackService.deleteFeedback(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
