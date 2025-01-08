package com.triplehelix.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.oauth2.sdk.Scope.Value;
import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;
import com.triplehelix.entities.BookingTimeSlot;
import com.triplehelix.entities.User;
import com.triplehelix.entities.UserRequest;
import com.triplehelix.services.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping
	public List<Map<String, Object>> getBookings() {
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/status")
	public List<Map<String, Object>> getBookingsByStatus(@RequestParam BookingStatus status) {
		return bookingService.getBookingsByStatus(status);
	}
	
	@GetMapping("/user")
	public List<Map<String, Object>> getBookingsByUserEmail(@RequestParam String email) {
		return bookingService.getBookingsByUserEmail(email);
	}
	
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) auth.getPrincipal();

        UserRequest userRequest = new UserRequest();

        userRequest.setUser(authenticatedUser);
        userRequest.setInstitute(booking.getUserRequest().getInstitute());
        
        booking.setStatus(BookingStatus.PENDING);
        booking.setReminderSent(false);
        booking.setFeedbackSent(false);

        Booking savedBooking = bookingService.createBooking(booking, userRequest); 
        
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }
    
    @PatchMapping("update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable int id, @RequestBody Map<String, Object> newBooking) {
        Optional<Booking> bookingOpt = bookingService.getBookingById(id);
        
        if (bookingOpt.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

        Booking booking = bookingOpt.get();
        
        newBooking.forEach((key, value) -> {
        	switch (key) {
				case "participantQuantity" -> booking.setParticipantQuantity((int) value);
				case "appointmentDate" -> booking.setAppointmentDate(LocalDate.parse(value.toString()));
				case "timeSlot" -> booking.setTimeSlot(BookingTimeSlot.valueOf((String) value));
				case "status" -> booking.setStatus(BookingStatus.valueOf((String) value));
				case "bookingInfoReq" -> booking.setBookingInfoReq(value.toString());
				case "reminderSent" -> booking.setReminderSent((boolean) value);
				case "feedbackSent" -> booking.setFeedbackSent((boolean) value);
        	}
        });
        
        booking.getUserRequest().setUpdatedAt(LocalDateTime.now());
        
        Booking savedBooking = bookingService.saveBooking(booking);
        
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

}
