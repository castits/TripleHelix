package com.triplehelix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String sendTo, String subject, String text) {
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(sendTo);
		email.setSubject(subject);
		email.setText(text);
		mailSender.send(email);
	}

}
