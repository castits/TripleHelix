package com.triplehelix.entities;

import java.time.LocalDateTime;

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
	private LocalDateTime appointmentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BookingStatus status;
	
	@Column(name = "booking_info_req")
	private String bookingInfoReq;
	
	@Column(name = "reminder_sent")
	private boolean reminderSent;
	
	@Column(name = "feedback_sent")
	private boolean feedbackSent;
	
	@Column(name = "sign_up_confirmation_sent")
	private boolean signUpConfirmationSent;

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

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
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

	public boolean isSignUpConfirmationSent() {
		return signUpConfirmationSent;
	}

	public void setSignUpConfirmationSent(boolean signUpConfirmationSent) {
		this.signUpConfirmationSent = signUpConfirmationSent;
	}

}
