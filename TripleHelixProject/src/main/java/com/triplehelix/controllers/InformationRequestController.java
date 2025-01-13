package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.triplehelix.entities.InformationRequest;
import com.triplehelix.services.EmailService;

@RestController
@RequestMapping("/api/information-requests")
public class InformationRequestController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send")
	public void sendUserRequestEmail(@RequestBody InformationRequest informationRequest) {
		String userName = informationRequest.getUserName();
		String userSurname = informationRequest.getUserSurname();
		String userPhone = informationRequest.getUserPhone();
		String sendFrom = informationRequest.getUserEmail();
		String emailText = informationRequest.getInformationRequestText();
		
		emailService.sendEmail(sendFrom,
				"triplehelixtest1@gmail.com",
				"Richiesta di informazioni da parte di " + userName + " " + userSurname,
				emailText + "\n\n" + userName + " " + userSurname + ": " + userPhone);
	}

}
