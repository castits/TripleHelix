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
		String imagePath = "src/main/resources/static/assets/img/deck.jpg";
		String cid = "bannerImage";
		
		try {
			String informationRequestText = "<html>" +
                    "<head>" +
                    "<style>" +
                    "  body { font-family: Arial, sans-serif; color: #333333; }" +
                    "  .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; }" +
                    "  h1 { color: #ff8400; font-size: 22px; text-align: center; }" +
                    "  p { line-height: 1.6; font-size: 16px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
                    "    <h1>Richiesta di informazioni</h1>" +
                    "    <p><strong>Nome:</strong> " + userName + " " + userSurname + "</p>" +
                    "    <p><strong>Email:</strong> " + sendFrom + "</p>" +
                    "    <p><strong>Telefono:</strong> " + (userPhone != "" ? userPhone : "N/D" ) + "</p>" +
                    "    <p><strong>Messaggio:</strong></p>" +
                    "    <p>" + emailText + "</p>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			emailService.sendEmail(sendFrom,
					"triplehelixtest1@gmail.com",
					"Richiesta di informazioni da parte di " + userName + " " + userSurname,
					informationRequestText,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send request email from " + sendFrom);
		}
		
		try {
			String responseEmail = "<html>" +
                    "<head>" +
                    "<style>" +
                    "  body { font-family: Arial, sans-serif; color: #333333; }" +
                    "  .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; }" +
                    "  h1 { color: #ff8400; font-size: 24px; text-align: center; }" +
                    "  p { line-height: 1.6; font-size: 16px; }" +
                    "  .footer { margin-top: 20px; font-size: 14px; color: #777777; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
                    "    <h1>Grazie per la tua richiesta!</h1>" +
                    "    <p>Gentile <strong>" + userName + " " + userSurname + "</strong>,</p>" +
                    "    <p>Grazie per averci contattato. Abbiamo ricevuto la tua richiesta e il nostro team ti risponderà al più presto.</p>" +
                    "    <p>Nel frattempo, se hai bisogno di aggiungere informazioni o hai altre domande, sentiti libero di rispondere a questa email.</p>" +
                    "    <p>Siamo qui per aiutarti!</p>" +
                    "    <div class='footer'>" +
                    "      <p>Con i migliori saluti,</p>" +
                    "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			emailService.sendEmail(sendFrom,
					"Grazie per la tua richiesta",
					responseEmail,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send reply email to " + sendFrom);
		}
	}

}
