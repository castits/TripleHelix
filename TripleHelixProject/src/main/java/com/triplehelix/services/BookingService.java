package com.triplehelix.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;
import com.triplehelix.entities.UserRequest;
import com.triplehelix.repos.BookingDAO;

@Service
public class BookingService {
	
	@Autowired
	private BookingDAO bookingDAO;
	
	@Autowired
	private UserRequestService userRequestService;
	
	private Map<String, Object> bookingToMap(Booking booking) {
	    return Map.of(
	    	"bookingId", booking.getBookingId(),
	    	"userName", booking.getUserRequest().getUser().getUserName(),
	    	"userSurname", booking.getUserRequest().getUser().getUserSurname(),
	    	"institute",  booking.getUserRequest().getInstitute(),
	    	"day", booking.getAppointmentDate().getDayOfWeek(),
	        "appointmentDate", booking.getAppointmentDate(),
	        "timeSlot", booking.getTimeSlot().toString(),
	        "participantQuantity", booking.getParticipantQuantity(),
	        "userEmail", booking.getUserRequest().getUser().getUserEmail()
	    );
	}

	public List<Map<String, Object>> getAllBookings() {
		return bookingDAO.findAll()
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}
	
	public Optional<Booking> getBookingById(int id) {
		return bookingDAO.findById(id);
	}
	
	public List<Map<String, Object>> getBookingsByStatus(BookingStatus status) {
		return bookingDAO.findByStatus(status)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}

	public List<Map<String, Object>> getBookingsByUserEmail(String email) {
		return bookingDAO.findByUserRequest_User_UserEmail(email)
			.stream()
			.map(this::bookingToMap)
			.collect(Collectors.toList());
	}
	
	public Booking createBooking(Booking booking, UserRequest userRequest) {
		UserRequest savedUserRequest = userRequestService.createUserRequest(userRequest);
		booking.setUserRequest(savedUserRequest);
		return bookingDAO.save(booking);
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
	
	public void deleteBookingById(int id) {
		bookingDAO.deleteById(id);
	}
	
	public void changeBookingStatus(int id, String status) {
		Optional<Booking> booking = bookingDAO.findById(id);
		
		if (booking.isPresent()) {
			Booking newBooking = booking.get();
			newBooking.setStatus(BookingStatus.valueOf(status));
			bookingDAO.save(newBooking);
		}
		

	}

}
