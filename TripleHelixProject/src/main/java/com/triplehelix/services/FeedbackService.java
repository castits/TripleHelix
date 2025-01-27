package com.triplehelix.services;

import com.triplehelix.entities.Feedback;
import com.triplehelix.exceptions.FeedbackNotFoundException;
import com.triplehelix.repos.FeedbackDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class handles the logic related to feedbacks operations
 */
@Service
public class FeedbackService {
	
	// Dependency injection for the feedback repository
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	/**
	 * Save a new feedback to the database
	 * @param feedback - the feedback to be saved
	 * @return the saved feedback
	 */
	public Feedback saveFeedback(Feedback feedback) {
		return feedbackDAO.save(feedback);
	}

    /**
     * Returns all feedbacks
     * @return a list of all the feedbacks in the database
     */
    public List<Feedback> getAllFeedbacks() {
        return feedbackDAO.findAll();
    }

    /**
     * Returns a feedback found by id
     * @param id - the id of the feedback to return
     * @return the feedback found by id
     * @throws FeedbackNotFoundException if the feedback is not present in the database
     */
    public Feedback getFeedbackById(int id) {
        return feedbackDAO.findById(id)
        	.orElseThrow(() -> new FeedbackNotFoundException("Feedback with id " + id + " not found"));
    }

	/**
	 * Delete a feedback passing its id
	 * @param id - the id of the feedback to be deleted
	 * @throws FeedbackNotFoundException if the booking does not exist
	 */
    public void deleteFeedback(int id) {
    	// If the feedback doesn't exist, throws an exception
    	if (!feedbackDAO.existsById(id)) {
            throw new IllegalArgumentException("Feedback with id " + id + " doesn't exist");
        }
        feedbackDAO.deleteById(id); // Delete the feedback
    }
}
