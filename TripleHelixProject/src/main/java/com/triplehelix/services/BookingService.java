package com.triplehelix.services;

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
	
	public Booking createBooking(Booking booking, UserRequest userRequest) {
		UserRequest savedUserRequest = userRequestService.createUserRequest(userRequest);
		booking.setUserRequest(savedUserRequest);
		return bookingDAO.save(booking);
	}

}
