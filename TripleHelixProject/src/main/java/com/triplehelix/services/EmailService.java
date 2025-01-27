package com.triplehelix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * This class handles the logic related to email operations
 */
@Service
public class EmailService {
	
	// Dependency injection for JavaMailSender to handle email operations
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * Send an email to a specific recipient
	 * @param sendTo - the recipient's email
	 * @param subject - the subject of the email
	 * @param text - the body of the email (HTML)
	 * @param imagePath - path of a image that will be included into the email
	 * @param cid - the content id for the image (used to embed it in the email)
	 * @throws MessagingException if there is an issue with email creation or sending
	 */
	public void sendEmail(String sendTo, String subject, String text, String imagePath, String cid) throws MessagingException {
		MimeMessage email = mailSender.createMimeMessage(); // Create a new MIME email message
		MimeMessageHelper helper = new MimeMessageHelper(email, true); // 'true' enables multipart mode for attachments
		
		helper.setTo(sendTo); // Set the recipient
		helper.setSubject(subject); // Set the subject
		helper.setText(text, true); // Set the HTML body. 'true' indicates the text is HTML
		
		// Add the image file and link it with the content id
		FileSystemResource file = new FileSystemResource(imagePath);
	    helper.addInline(cid, file);
	    
		mailSender.send(email); // Send the email
	}

	/**
	 * Send an email to a specific recipient from a specific address (reply to this address)
	 * @param sendFrom - the 'reply to' email
	 * @param sendTo - the recipient's email
	 * @param subject - the subject of the email
	 * @param text - the body of the email (HTML)
	 * @param imagePath - path of a image that will be included into the email
	 * @param cid - the content id for the image (used to embed it in the email)
	 * @throws MessagingException if there is an issue with email creation or sending
	 */
	public void sendEmail(String sendFrom, String sendTo, String subject, String text, String imagePath, String cid) throws MessagingException {
		MimeMessage email = mailSender.createMimeMessage(); // Create a new MIME email message
		MimeMessageHelper helper = new MimeMessageHelper(email, true); // 'true' enables multipart mode for attachments
		
		helper.setReplyTo(sendFrom); // Set the 'reply to' email address
		helper.setTo(sendTo); // Set the recipient
		helper.setSubject(subject); // Set the subject
		helper.setText(text, true); // Set the HTML body. 'true' indicates the text is HTML
		
		// Add the image file and link it with the content id
		FileSystemResource file = new FileSystemResource(imagePath);
	    helper.addInline(cid, file);
		
		mailSender.send(email); // Send the email
	}

}
