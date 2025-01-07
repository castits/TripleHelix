package com.triplehelix.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Feedback;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Integer> {

    /**
     * Find all feedback entries by booking ID.
     * 
     * @param bookingId the ID of the booking
     * @return a list of feedback entries associated with the booking ID
     */
    List<Feedback> findByBookingId(int bookingId);

    /**
     * Find all feedback entries with a specific rating.
     * 
     * @param rating the rating value to search for
     * @return a list of feedback entries with the specified rating
     */
    List<Feedback> findByRating(int rating);

    /**
     * Find feedback entries with comments containing a specific keyword.
     * 
     * @param keyword the keyword to search in the comments
     * @return a list of feedback entries containing the keyword in comments
     */
    List<Feedback> findByCommentContaining(String keyword);

}