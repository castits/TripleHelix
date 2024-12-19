package com.triplehelix.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @Column(name = "booking_id", nullable = false)
    private int bookingId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Feedback() {
        // Default constructor for JPA
    }

    public Feedback(int bookingId, int rating, String comment, LocalDateTime date) {
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
        this.date = date != null ? date : LocalDateTime.now();
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
