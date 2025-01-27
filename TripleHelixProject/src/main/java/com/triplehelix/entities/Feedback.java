package com.triplehelix.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Feedback entity that represents a feedback sent
 */
@Entity
@Table(name = "feedbacks")
public class Feedback {

	// Feedback id. Unique identifier for the feedback (AUTO_INCREMENT)
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    // Which lab did you attend?
    @Column(name = "which_lab", nullable = false)
    private String whichLab;
    
    // Was the lab formative? (1 to 5)
    @Column(name = "formative", nullable = false)
	private int formative;
    
    // Was the lab engaging? (1 to 5)
    @Column(name = "engaging", nullable = false)
	private int engaging;
    
    // Staff quality? (1 to 5)
    @Column(name = "staff_quality", nullable = false)
	private int staffQuality;
    
    // Do you recommend the lab?
    @Column(name = "recommend_lab", nullable = false)
	private String recommendLab;
    
    // Eventual advices
    @Column(name = "advices", nullable = true)
	private String advices;

    // Datetime that indicates when the feedback has been sent
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // Getters and Setters
    
	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getWhichLab() {
		return whichLab;
	}

	public void setWhichLab(String whichLab) {
		this.whichLab = whichLab;
	}

	public int getFormative() {
		return formative;
	}

	public void setFormative(int formative) {
		this.formative = formative;
	}

	public int getEngaging() {
		return engaging;
	}

	public void setEngaging(int engaging) {
		this.engaging = engaging;
	}

	public int getStaffQuality() {
		return staffQuality;
	}

	public void setStaffQuality(int staffQuality) {
		this.staffQuality = staffQuality;
	}

	public String getRecommendLab() {
		return recommendLab;
	}

	public void setRecommendLab(String recommendLab) {
		this.recommendLab = recommendLab;
	}

	public String getAdvices() {
		return advices;
	}

	public void setAdvices(String advices) {
		this.advices = advices;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
