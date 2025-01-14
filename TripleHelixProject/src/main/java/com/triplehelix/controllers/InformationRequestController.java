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
		
		emailService.sendEmail(sendFrom, "Grazie per la tua richiesta", "Ciao " + userName + " " + userSurname + ",\n\n"
				+ "Grazie mille per averci contattato. La tua richiesta è stata ricevuta dai nostri collaboratori che ti contatteranno al più presto.\r\n"
				+ "\r\n"
				+ "Nel frattempo, se hai necessità di aggiungere dettagli o hai altre richieste, non esitare a scriverci rispondendo a questa email.\r\n"
				+ "\r\n"
				+ "Ti ringraziamo. Siamo qui per aiutarti!\r\n"
				+ "\r\n"
				+ "Cordiali saluti,\r\n"
				+ "Cascina Caccia");
	}

}
