package com.triplehelix.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.InformationRequest;

@Repository

public interface InformationRequestDAO extends JpaRepository<InformationRequest, Integer> {
	
}
