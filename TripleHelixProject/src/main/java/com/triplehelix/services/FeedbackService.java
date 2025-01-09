package com.triplehelix.services;

import com.triplehelix.entities.Feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
	
	

	/*
    // Simulated in-memory database for feedbacks
    private final List<Feedback> feedbacks = new ArrayList<>();
    private int currentId = 1; // Counter to generate unique IDs

    // Retrieve all feedbacks
    public List<Feedback> getAllFeedbacks() {
        return new ArrayList<>(feedbacks); // Return a copy of the list
    }

    // Retrieve a feedback by ID
    public Optional<Feedback> getFeedbackById(int id) {
        return feedbacks.stream()
                .filter(feedback -> feedback.getFeedbackId() == id)
                .findFirst();
    }

    // Save a new feedback
    public Feedback saveFeedback(Feedback feedback) {
        feedback.setFeedbackId(currentId++); // Assign a unique ID
        feedbacks.add(feedback); // Add feedback to the list
        return feedback;
    }

    // Update an existing feedback
    public Optional<Feedback> updateFeedback(int id, Feedback feedbackDetails) {
        Optional<Feedback> existingFeedback = getFeedbackById(id); // Find the feedback

        if (existingFeedback.isPresent()) {
            Feedback feedback = existingFeedback.get();
            feedback.setBookingId(feedbackDetails.getBookingId()); // Update booking ID
            feedback.setRating(feedbackDetails.getRating()); // Update rating
            feedback.setComment(feedbackDetails.getComment()); // Update comment
            feedback.setDate(feedbackDetails.getDate()); // Update date
            return Optional.of(feedback);
        }
        return Optional.empty(); // Return empty if feedback not found
    }

    // Delete a feedback by ID
    public boolean deleteFeedback(int id) {
        return feedbacks.removeIf(feedback -> feedback.getFeedbackId() == id); // Remove by ID
    }
    */
}
