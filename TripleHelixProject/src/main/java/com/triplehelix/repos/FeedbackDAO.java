package com.triplehelix.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Feedback;

/**
 * This interface provides Feedbacks access in the database
 * It extends JpaRepository, which contains default queries
 * for CRUD operations and allows defining custom query methods
 */
@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Integer> {

}