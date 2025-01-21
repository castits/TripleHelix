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

@Service
public class BookingService {
	
	@Autowired
	private BookingDAO bookingDAO;
	
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

	public List<Map<String, Object>> getAllBookings() {
		return bookingDAO.findAll()
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}
	
	public Booking getBookingById(int id) {
		return bookingDAO.findById(id)
			.orElseThrow(() -> new BookingNotFoundException("Booking with id " + id + " not found"));
	}
	
	public List<Map<String, Object>> getBookingsByStatus(BookingStatus status) {
		return bookingDAO.findByStatus(status)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}

	public List<Map<String, Object>> getBookingsByUserEmail(String email) {
		return bookingDAO.findByUser_UserEmail(email)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}

	public List<Booking> getBookingsForReminder() {
		LocalDate datePlusThreeDays = LocalDate.now().plusDays(3);
		return bookingDAO.findByAppointmentDate(datePlusThreeDays);
	}
	
	public List<Booking> getBookingsForFeedback() {
		LocalDate dateMinusSevenDays = LocalDate.now().minusDays(7);
		return bookingDAO.findByAppointmentDate(dateMinusSevenDays);
	}
	
	public Booking saveBooking(Booking booking) {
		return bookingDAO.save(booking);
	}
	
	public Booking updateBooking(int id, Map<String, Object> updates) {
	    Booking booking = bookingDAO.findById(id)
	            .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + id + " not found"));

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
	
	public void deleteBookingById(int id) {
		if (!bookingDAO.existsById(id)) {
			throw new BookingNotFoundException("Cannot delete booking. Booking with id " + id + " doesn't exist");
		}
		bookingDAO.deleteById(id);
	}
	
	public void changeBookingStatus(int id, String status) {
		Booking booking = getBookingById(id);
		try {
            booking.setStatus(BookingStatus.valueOf(status));
            bookingDAO.save(booking);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

	}

}
