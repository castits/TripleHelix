package com.triplehelix.entities;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.*;

@Entity
@Table(name = "information_request")
public class InformationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "information_request_id")
    private int informationRequestId;

    @Column(name = "request_id", nullable = false)
    private int requestId;


    @Column(name = "information_request_text", nullable = false, length = 1000)
    private String informationRequestText;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "user_surname", length = 100)
    private String userSurname;

    @Column(name = "user_email", unique = true, nullable = false, length = 150)
    private String userEmail;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    // Costruttore predefinito
    public InformationRequest() {
    }

    // Costruttore parametrizzato
    public InformationRequest(int requestId, String informationRequestText, String userEmail) {
        this.requestId = requestId;
        this.informationRequestText = informationRequestText;
        this.userEmail = userEmail;
    }

    // Getter e setter
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

    @Override
    public String toString() {
        return "InformationRequest{" +
                "informationRequestId=" + informationRequestId +
                ", requestId=" + requestId +
                ", informationRequestText='" + informationRequestText + '\'' +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
