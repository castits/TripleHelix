package com.triplehelix.entities;

public class InformationRequest {

    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPhone;
    private String informationRequestText;

    public InformationRequest() {}

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
