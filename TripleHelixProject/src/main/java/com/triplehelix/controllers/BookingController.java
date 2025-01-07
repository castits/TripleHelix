package com.triplehelix.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.UserRequest;
import com.triplehelix.services.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping
	public List<Booking> getBookings() {
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/user")
	public Optional<Booking> getBookingsByUserEmail(@RequestParam String email) {
		return bookingService.getBookingsByUserEmail(email);
	}
	
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUser(booking.getUserRequest().getUser());
        userRequest.setInstitute(booking.getUserRequest().getInstitute());
        userRequest.setStatus(booking.getUserRequest().getStatus());

        Booking savedBooking = bookingService.createBooking(booking, userRequest); 
        
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

}
