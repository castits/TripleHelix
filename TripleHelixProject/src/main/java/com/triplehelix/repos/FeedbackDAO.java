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
    //List<Feedback> findByBookingId(int bookingId);

}