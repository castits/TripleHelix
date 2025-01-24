package com.triplehelix.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;
import com.triplehelix.entities.User;
import com.triplehelix.exceptions.BookingNotFoundException;
import com.triplehelix.services.BookingService;
import com.triplehelix.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Map<String, Object>>> getBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}
	
	@GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getBookingsByStatus(@RequestParam String status) {
		try {
	        BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
	        return ResponseEntity.ok(bookingService.getBookingsByStatus(bookingStatus));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(status + " is not a valid status. Allowed values are: " 
	                      + List.of(BookingStatus.values()));
	    }
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Map<String, Object>>> getBookingsByUserEmail(@RequestParam String email) {
		return ResponseEntity.ok(bookingService.getBookingsByUserEmail(email));
	}
	
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        User authenticatedUser = userService.getAuthenticatedUser();
        
        booking.setUser(authenticatedUser);
        booking.setStatus(BookingStatus.PENDING);
        booking.setReminderSent(false);
        booking.setFeedbackSent(false);

        Booking savedBooking = bookingService.saveBooking(booking); 
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }
    
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBooking(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, updates);
            return ResponseEntity.ok(updatedBooking);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeBookingStatus(@PathVariable int id, @RequestParam String status) {
        try {
            bookingService.changeBookingStatus(id, status);
            return ResponseEntity.ok("Status updated successfully");
        } catch (BookingNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBookingById(@PathVariable int id) {
        try {
            bookingService.deleteBookingById(id);
            return ResponseEntity.ok("Booking deleted successfully");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
