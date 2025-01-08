package com.triplehelix.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@Column(name = "booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	@OneToOne
	@JoinColumn(name = "request_id")
	private UserRequest userRequest;
	
	@Column(name = "participant_quantity")
	private int participantQuantity;
	
	@Column(name = "appointment_date")
	private LocalDate appointmentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "time_slot")
	private BookingTimeSlot timeSlot;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BookingStatus status;
	
	@Column(name = "booking_info_req")
	private String bookingInfoReq;
	
	@Column(name = "reminder_sent")
	private boolean reminderSent;
	
	@Column(name = "feedback_sent")
	private boolean feedbackSent;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public UserRequest getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(UserRequest userRequest) {
		this.userRequest = userRequest;
	}

	public int getParticipantQuantity() {
		return participantQuantity;
	}

	public void setParticipantQuantity(int participantQuantity) {
		this.participantQuantity = participantQuantity;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public BookingTimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(BookingTimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public String getBookingInfoReq() {
		return bookingInfoReq;
	}

	public void setBookingInfoReq(String bookingInfoReq) {
		this.bookingInfoReq = bookingInfoReq;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}

	public boolean isFeedbackSent() {
		return feedbackSent;
	}

	public void setFeedbackSent(boolean feedbackSent) {
		this.feedbackSent = feedbackSent;
	}

}
