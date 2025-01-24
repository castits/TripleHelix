package com.triplehelix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String sendTo, String subject, String text) throws MessagingException {
		MimeMessage email = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(email, true);
		
		helper.setTo(sendTo);
		helper.setSubject(subject);
		helper.setText(text, true);
		mailSender.send(email);
	}

	public void sendEmail(String sendFrom, String sendTo, String subject, String text) throws MessagingException {
		MimeMessage email = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(email, true);
		
		helper.setReplyTo(sendFrom);
		helper.setTo(sendTo);
		helper.setSubject(subject);
		helper.setText(text, true);
		mailSender.send(email);
	}

}
