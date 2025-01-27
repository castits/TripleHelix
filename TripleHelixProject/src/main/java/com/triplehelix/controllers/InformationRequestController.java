package com.triplehelix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.triplehelix.entities.InformationRequest;
import com.triplehelix.services.EmailService;

/**
 * This controller handles information requests operations
 */
@RestController
@RequestMapping("/api/information-requests") // Default endpoint for information requests operations
public class InformationRequestController {

	// Dependency injection for the email service
	@Autowired
	private EmailService emailService;
	
	/**
	 * Endpoint that send an information request email to the admin
	 * and a response email to the user that sent the information request
	 * @param informationRequest - an InformationRequest in the body
	 */
	@PostMapping("/send")
	public void sendUserRequestEmail(@RequestBody InformationRequest informationRequest) {
		// Get the information request data to prepare the email
		String userName = informationRequest.getUserName();
		String userSurname = informationRequest.getUserSurname();
		String userPhone = informationRequest.getUserPhone();
		String sendFrom = informationRequest.getUserEmail();
		String emailText = informationRequest.getInformationRequestText();
		
		// Prepare the email
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
                    "  .banner { display: block; max-width: 100%; height: auto; margin: 20px auto 0; border-radius: 8px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <h1>Richiesta di informazioni</h1>" +
                    "    <p><strong>Nome:</strong> " + userName + " " + userSurname + "</p>" +
                    "    <p><strong>Email:</strong> " + sendFrom + "</p>" +
                    "    <p><strong>Telefono:</strong> " + (userPhone != "" ? userPhone : "N/D" ) + "</p>" +
                    "    <p><strong>Messaggio:</strong></p>" +
                    "    <p>" + emailText + "</p>" +
                    "    <img src='cid:" + cid + "' alt='Banner' class='banner' />" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			// Send email to the admin
			emailService.sendEmail(sendFrom,
					"triplehelixtest1@gmail.com",
					"Richiesta di informazioni da parte di " + userName + " " + userSurname,
					informationRequestText,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send request email from " + sendFrom); // Print an error if the email can't be sent
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
                    "  .banner { display: block; max-width: 100%; height: auto; margin: 20px auto 0; border-radius: 8px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <h1>Grazie per la tua richiesta!</h1>" +
                    "    <p>Gentile <strong>" + userName + " " + userSurname + "</strong>,</p>" +
                    "    <p>Grazie per averci contattato. Abbiamo ricevuto la tua richiesta e il nostro team ti risponderà al più presto.</p>" +
                    "    <p>Nel frattempo, se hai bisogno di aggiungere informazioni o hai altre domande, sentiti libero di rispondere a questa email.</p>" +
                    "    <p>Siamo qui per aiutarti!</p>" +
                    "    <div class='footer'>" +
                    "      <p>Con i migliori saluti,</p>" +
                    "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
                    "    </div>" +
                    "    <img src='cid:" + cid + "' alt='Banner' class='banner' />" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			// Send response email to the user
			emailService.sendEmail(sendFrom,
					"Grazie per la tua richiesta",
					responseEmail,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send reply email to " + sendFrom); // Print an error if the email can't be sent
		}
	}
}
