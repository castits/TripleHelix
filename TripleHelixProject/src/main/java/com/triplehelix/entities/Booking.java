package com.triplehelix.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Booking {

	@Id
	@Column(name = "booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	@Column(name = "request_id")
	private int requestId;
	@Column(name = "participant_quantity")
	private int participantQuantity;
	@Column(name = "appointment_date")
	private LocalDateTime appointmentDate;
	@Column(name = "booking_info_req")
	private String bookingInfoReq;
	@Column(name = "event_confirmation_sent")
	private boolean eventConfirmationSent;
	@Column(name = "reminder_sent")
	private boolean reminderSent;
	@Column(name = "feedback_sent")
	private boolean feedbackSent;
	@Column(name = "sign_up_confirmation_sent")
	private boolean signUpConfirmationSent;
	
	public Booking(int requestId, int participantQuantity, LocalDateTime appointmentDate, String bookingInfoReq,
			boolean eventConfirmationSent, boolean reminderSent, boolean feedbackSent, boolean signUpConfirmationSent) {
		this.requestId = requestId;
		this.participantQuantity = participantQuantity;
		this.appointmentDate = appointmentDate;
		this.bookingInfoReq = bookingInfoReq;
		this.eventConfirmationSent = eventConfirmationSent;
		this.reminderSent = reminderSent;
		this.feedbackSent = feedbackSent;
		this.signUpConfirmationSent = signUpConfirmationSent;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

	public String getBookingInfoReq() {
		return bookingInfoReq;
	}

	public void setBookingInfoReq(String bookingInfoReq) {
		this.bookingInfoReq = bookingInfoReq;
	}

	public boolean isEventConfirmationSent() {
		return eventConfirmationSent;
	}

	public void setEventConfirmationSent(boolean eventConfirmationSent) {
		this.eventConfirmationSent = eventConfirmationSent;
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
