package com.triplehelix.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Booking;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Integer> {
	
	

}