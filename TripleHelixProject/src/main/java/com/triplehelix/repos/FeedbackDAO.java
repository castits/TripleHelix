package com.triplehelix.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Feedback;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Integer> {

}