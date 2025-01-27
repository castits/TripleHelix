package com.triplehelix.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;
import com.triplehelix.entities.BookingTimeSlot;
import com.triplehelix.exceptions.BookingNotFoundException;
import com.triplehelix.repos.BookingDAO;

/**
 * This class handles the logic related to bookings operations
 */
@Service
public class BookingService {
	
	// Dependency injection for the booking repository
	@Autowired
	private BookingDAO bookingDAO;
	
	/**
	 * This method allows to convert a booking to a Map
	 * Used to represent bookings just with some data
	 * @param booking - the booking to be converted to a Map
	 * @return a Map that contains some booking details
	 */
	private Map<String, Object> bookingToMap(Booking booking) {
	    return Map.of(
	    	"bookingId", booking.getBookingId(),
	    	"userName", booking.getUser().getUserName(),
	    	"userSurname", booking.getUser().getUserSurname(),
	    	"institute",  booking.getInstitute(),
	    	"day", booking.getAppointmentDate().getDayOfWeek(),
	        "appointmentDate", booking.getAppointmentDate(),
	        "timeSlot", booking.getTimeSlot().toString(),
	        "participantQuantity", booking.getParticipantQuantity(),
	        "userEmail", booking.getUser().getUserEmail()
	    );
	}

	/**
	 * Returns all bookings converted to a list of Maps
	 * @return a list of Maps that represents all booking in the database
	 */
	public List<Map<String, Object>> getAllBookings() {
		return bookingDAO.findAll()
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}
	
	/**
	 * Returns a booking found by id
	 * @param id - the id of the booking to return
	 * @return the booking found by id
	 * @throws BookingNotFoundException if the booking is not present in the database
	 */
	public Booking getBookingById(int id) {
		return bookingDAO.findById(id)
			.orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " not found"));
	}
	
	/**
	 * Returns a mapped list of bookings found by their status
	 * @param status - the status of the booking to filter
	 * @return a list of Maps representing bookings filtered by status
	 */
	public List<Map<String, Object>> getBookingsByStatus(BookingStatus status) {
		return bookingDAO.findByStatus(status)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}

	/**
	 * Returns a mapped list of specific user's bookings
	 * @param email - a user's email
	 * @return a list of Maps representing the user's bookings
	 */
	public List<Map<String, Object>> getBookingsByUserEmail(String email) {
		return bookingDAO.findByUser_UserEmail(email)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}

	/**
	 * Returns a list of bookings that need reminders
	 * The reminder will be sent when the appointment date is in 3 days
	 * Uses the findByAppointmentDate() method from the BookingDAO repository
	 * @return a list of bookings that need reminders
	 */
	public List<Booking> getBookingsForReminder() {
		LocalDate datePlusThreeDays = LocalDate.now().plusDays(3); // Add 3 days from today
		return bookingDAO.findByAppointmentDate(datePlusThreeDays);
	}
	
	/**
	 * Returns a list of bookings that need feedback email
	 * The feedback email will be sent when the appointment date was 3 days ago
	 * Uses the findByAppointmentDate() method from the BookingDAO repository
	 * @return a list of bookings that need reminders
	 */
	public List<Booking> getBookingsForFeedback() {
		LocalDate dateMinusSevenDays = LocalDate.now().minusDays(3); // Remove 3 days from today
		return bookingDAO.findByAppointmentDate(dateMinusSevenDays);
	}
	
	/**
	 * Saves a new booking to the database
	 * @param booking - the booking to be saved
	 * @return the saved booking
	 */
	public Booking saveBooking(Booking booking) {
		return bookingDAO.save(booking);
	}
	
	/**
	 * Updates an existing booking
	 * @param id - the id of the booking to be updated
	 * @param updates - a Map of booking fields to update
	 * @return the updated booking
	 * @throws BookingNotFoundException if the booking does not exist
	 * @throws IllegalArgumentException if a field is invalid or not updatable
	 */
	public Booking updateBooking(int id, Map<String, Object> updates) {
		// Find a booking by id
	    Booking booking = bookingDAO.findById(id)
	            .orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " doesn't exist"));

	    // For each attribute present in the Map, update it
	    updates.forEach((key, value) -> {
	        switch (key) {
	            case "institute":
	                booking.setInstitute((String) value);
	                break;
	            case "participantQuantity":
	                booking.setParticipantQuantity((Integer) value);
	                break;
	            case "appointmentDate":
	                booking.setAppointmentDate(LocalDate.parse((String) value));
	                break;
	            case "timeSlot":
	                booking.setTimeSlot(BookingTimeSlot.valueOf((String) value));
	                break;
	            case "activity":
	                booking.setActivity((String) value);
	                break;
	            case "status":
	                booking.setStatus(BookingStatus.valueOf((String) value));
	                break;
	            default:
	                throw new IllegalArgumentException("Field '" + key + "' is not updatable or invalid");
	        }
	    });

	    return bookingDAO.save(booking);
	}
	
	/**
	 * Delete a booking passing its id
	 * @param id - the id of the booking to be deleted
	 * @throws BookingNotFoundException if the booking does not exist
	 */
	public void deleteBookingById(int id) {
		// If the booking doesn't exist, throws an exception
		if (!bookingDAO.existsById(id)) {
			throw new BookingNotFoundException("Cannot delete booking. Booking with id " + id + " doesn't exist");
		}
		bookingDAO.deleteById(id); // Delete the booking
	}
	
	/**
	 * Changes the status of a booking
	 * @param id - the id of the booking to be updated
	 * @param status - the new status for the booking
	 * @throws IllegalArgumentException if the status passed as parameter is invalid
	 */
	public void changeBookingStatus(int id, String status) {
		Booking booking = getBookingById(id); // Find a booking by id
		try {
            booking.setStatus(BookingStatus.valueOf(status)); // Set the new status
            bookingDAO.save(booking); // Save the booking to the database
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status); // If the status is invalid, throws an exception
        }

	}

}
