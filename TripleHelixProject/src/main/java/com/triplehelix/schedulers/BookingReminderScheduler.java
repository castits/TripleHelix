package com.triplehelix.schedulers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.triplehelix.entities.Booking;
import com.triplehelix.repos.BookingDAO;
import com.triplehelix.services.BookingService;
import com.triplehelix.services.EmailService;

import jakarta.mail.MessagingException;

@Component
public class BookingReminderScheduler {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BookingDAO bookingDAO;
	
	final String DATE_FORMAT = "dd/MM/yyyy";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	@Scheduled(cron = "* * * * * ?")
	public void sendBookingsReminders() throws MessagingException {
		List<Booking> bookings = bookingService.getBookingsForReminder();
		
		for (Booking booking : bookings) {
			if (!booking.isReminderSent()) {
				String sendTo = booking.getUser().getUserEmail();
				String formattedDate = formatter.format(booking.getAppointmentDate());
				String subject = "Promemoria Visita del " + formattedDate;
				String body = "Buongiorno " + booking.getUser().getUserName() + ",\n\n"
						+ "Ti ricordiamo che la visita presso la nostra struttura sarà il " + formattedDate;
				
				try {
					emailService.sendEmail(sendTo, subject, body);					
					booking.setReminderSent(true);
					bookingDAO.save(booking);
				} catch (Exception e) {
					System.err.println("Failed to send reminder email to " + sendTo);
				}
			}
		}

	}

}
