package com.triplehelix.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Integer> {
	
	public List<Booking> findByStatus(BookingStatus status);
	public List<Booking> findByUserRequest_User_UserEmail(String email);
	public List<Booking> findByAppointmentDate(LocalDate date);

}
