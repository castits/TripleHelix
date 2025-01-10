package com.triplehelix.services;

import com.triplehelix.entities.Feedback;
import com.triplehelix.repos.FeedbackDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Feedback> getFeedbackById(int id) {
        return feedbackDAO.findById(id);
    }

    // Delete a feedback by ID
    public void deleteFeedback(int id) {
        feedbackDAO.deleteById(id);
    }
}
