package com.triplehelix.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @Column(name = "which_lab", nullable = false)
    private String whichLab;
    
    @Column(name = "formative", nullable = false)
	private int formative;
    
    @Column(name = "engaging", nullable = false)
	private int engaging;
    
    @Column(name = "staff_quality", nullable = false)
	private int staffQuality;
    
    @Column(name = "recommend_lab", nullable = false)
	private String recommendLab;
    
    @Column(name = "advices", nullable = true)
	private String advices;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

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
