package com.triplehelix.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.UserRequest;
import com.triplehelix.repos.BookingDAO;

@Service
public class BookingService {
	
	@Autowired
	private BookingDAO bookingDAO;
	
	@Autowired
	private UserRequestService userRequestService;
	
	public List<Booking> getAllBookings() {
		return bookingDAO.findAll();
	}
	
	public Optional<Booking> getBookingsByUserEmail(String email) {
		return bookingDAO.findByUserRequest_User_UserEmail(email);
	}
	
	public Booking createBooking(Booking booking, UserRequest userRequest) {
		UserRequest savedUserRequest = userRequestService.createUserRequest(userRequest);
		booking.setUserRequest(savedUserRequest);
		return bookingDAO.save(booking);
	}
	
	public List<Booking> getBookingsForReminder() {
		LocalDateTime datePlusThreeDays = LocalDate.now().plusDays(3).atStartOfDay();
		return bookingDAO.findByAppointmentDate(datePlusThreeDays);
	}

}
