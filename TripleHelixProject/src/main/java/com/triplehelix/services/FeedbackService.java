package com.triplehelix.services;

import com.triplehelix.entities.Feedback;
import com.triplehelix.exceptions.FeedbackNotFoundException;
import com.triplehelix.repos.FeedbackDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
	
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	public Feedback saveFeedback(Feedback feedback) {
		return feedbackDAO.save(feedback);
	}

    // Retrieve all feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackDAO.findAll();
    }

    // Retrieve a feedback by ID
    public Feedback getFeedbackById(int id) {
        return feedbackDAO.findById(id)
        	.orElseThrow(() -> new FeedbackNotFoundException("Feedback with id " + id + " not found"));
    }

    // Delete a feedback by ID
    public void deleteFeedback(int id) {
    	if (!feedbackDAO.existsById(id)) {
            throw new IllegalArgumentException("Feedback with id " + id + " doesn't exist");
        }
        feedbackDAO.deleteById(id);
    }
}
