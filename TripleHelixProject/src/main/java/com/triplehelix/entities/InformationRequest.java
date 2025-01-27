package com.triplehelix.entities;

/**
 * InformationRequest class that describe a request that an user can send
 */
public class InformationRequest {

	// User's name
    private String userName;
    // User's surname
    private String userSurname;
    // User's email
    private String userEmail;
    // User's phone number
    private String userPhone;
    // Text written by the user
    private String informationRequestText;

    /**
     * Default constructor
     */
    public InformationRequest() {}

    // Getters and Setters
    
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
