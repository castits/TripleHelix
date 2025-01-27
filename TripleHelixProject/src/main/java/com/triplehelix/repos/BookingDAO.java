package com.triplehelix.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;

/**
 * This interface provides bookings access in the database
 * It extends JpaRepository, which contains default queries
 * for CRUD operations and allows defining custom query methods
 */
@Repository
public interface BookingDAO extends JpaRepository<Booking, Integer> {
	
	/**
	 * Returns a list of bookings by status
	 * @param status - the booking's status
	 * @return a list of bookings filtered by status
	 */
	public List<Booking> findByStatus(BookingStatus status);
	
	/**
	 * Returns a list of bookings by the email of a user
	 * This method returns every booking of a specific user
	 * using the relationship between Booking and User entities
	 * @param email - a user's email
	 * @return a list of bookings filtered by user email
	 */
	public List<Booking> findByUser_UserEmail(String email);
	
	/**
	 * Returns a list of bookings by the email of a user and their status
	 * This method returns every booking of a specific user
	 * using the relationship between Booking and User entities
	 * and with a specific status
	 * @param email - a user's email
	 * @param status - a booking status
	 * @return a list of bookings filtered by user email and booking status
	 */
	public List<Booking> findByUser_UserEmailAndStatus(String email, BookingStatus status);
	
	/**
	 * Returns a list of bookings scheduled for a specific date
	 * @param date - the appointment date
	 * @return a list of bookings filtered by appointment date
	 */
	public List<Booking> findByAppointmentDate(LocalDate date);

}
