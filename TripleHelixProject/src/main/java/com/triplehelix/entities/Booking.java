package com.triplehelix.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Booking entity that represents a booking made by a user
 */
@Entity
@Table(name = "bookings")
public class Booking {

	// Booking id. Unique identifier for the booking (AUTO_INCREMENT)
	@Id
	@Column(name = "booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	// Many to one relationship with users: a user can make multiple bookings
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	// Institute name
	@Column(name = "institute", nullable = false)
	private String institute;
	
	// Number of participant at the event
	@Column(name = "participant_quantity", nullable = false)
	private int participantQuantity;
	
	// Appointment date
	@Column(name = "appointment_date", nullable = false)
	private LocalDate appointmentDate;
	
	// Enum for the time slot of the visit (MORNING, AFTERNOON, FULL_DAY)
	@Enumerated(EnumType.STRING)
	@Column(name = "time_slot", nullable = false)
	private BookingTimeSlot timeSlot;
	
	// The activity requested
	@Column(name = "activity", nullable = false)
	private String activity;
	
	// Enum for the current booking status (PENDING, CONFIRMED, REFUSED)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private BookingStatus status;
	
	// Message that the user could add during the booking request (optional)
	@Column(name = "booking_info_req", nullable = true)
	private String bookingInfoReq;
	
	// Boolean that indicates if the reminder email has been sent
	@Column(name = "reminder_sent", nullable = false)
	private boolean reminderSent;

	// Boolean that indicates if the feedback email has been sent
	@Column(name = "feedback_sent", nullable = false)
	private boolean feedbackSent;
	
	// Timestamp when the booking was created; not updatable
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	// Timestamp for the last update of the booking
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	/**
     * Lifecycle callback to set createdAt and updatedAt before persisting the entity
     */
	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

	/**
     * Lifecycle callback to update updatedAt before updating the entity
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    //Getters and Setters
    
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
