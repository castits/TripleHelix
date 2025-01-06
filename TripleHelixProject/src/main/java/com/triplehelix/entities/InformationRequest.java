

package com.triplehelix.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Represents the entity for the "information_request" table in the database.
 * This class maps to the "information_request" table and includes fields and
 * configurations for Hibernate/JPA.
 */
@Entity
@Table(name = "information_request")
public class InformationRequest {

    // Primary key for the table with auto-increment strategy.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to AUTO_INCREMENT in the database
    @Column(name = "information_request_id")
    private int informationRequestId;

    // Column for the request ID (mandatory field).
    @Column(name = "request_id", nullable = false)
    private int requestId;

    // Column for the text of the information request (mandatory field).
    @Column(name = "information_request_text", nullable = false)
    private String informationRequestText;

    // Column for the user's first name (optional field).
    @Column(name = "user_name")
    private String userName;

    // Column for the user's last name (optional field).
    @Column(name = "user_surname")
    private String userSurname;

    // Column for the user's email (mandatory and unique field).
    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    // Column for the user's phone number (optional field).
    @Column(name = "user_phone", nullable = true)
    private String userPhone;

    /*
     * Parameterized constructor to initialize all fields of the entity.
     *
     * @param informationRequestId the ID of the information request
     * @param requestId the ID of the request type
     * @param informationRequestText the text of the information request
     * @param userName the user's first name
     * @param userSurname the user's last name
     * @param userEmail the user's email address
     * @param userPhone the user's phone number
     */
    
    public InformationRequest(int informationRequestId, int requestId, String informationRequestText,
                              String userName, String userSurname, String userEmail, String userPhone) {
        super();
        this.informationRequestId = informationRequestId;
        this.requestId = requestId;
        this.informationRequestText = informationRequestText;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    /*
     * Default constructor required by JPA/Hibernate.
     */
    
    public InformationRequest() {
        // Default constructor
    }

    // Getter and setter methods for each field:

    public int getInformationRequestId() {
        return informationRequestId;
    }

    public void setInformationRequestId(int informationRequestId) {
        this.informationRequestId = informationRequestId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getInformationRequestText() {
        return informationRequestText;
    }

    public void setInformationRequestText(String informationRequestText) {
        this.informationRequestText = informationRequestText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
