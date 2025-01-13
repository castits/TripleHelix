package com.triplehelix.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triplehelix.entities.Feedback;
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

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable int id) {
        feedbackService.deleteFeedback(id);
    }

}
