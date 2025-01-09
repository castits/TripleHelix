package com.triplehelix.schedulers;

import java.util.List;
import java.util.Optional;

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
	
	@Scheduled(cron = "* * * * * ?")
	public void sendBookingsReminders() throws MessagingException {
		List<Booking> bookings = bookingService.getBookingsForReminder();
		
		for (Booking booking : bookings) {
			if (!booking.isReminderSent()) {
				String sendTo = booking.getUserRequest().getUser().getUserEmail();
				String subject = "Promemoria Visita del " + booking.getAppointmentDate();
				String body = "Buongiorno " + booking.getUserRequest().getUser().getUserName() + ",\n\n"
						+ "Ti ricordiamo che la visita presso la nostra struttura sarà il " + booking.getAppointmentDate();
				
				emailService.sendEmail(sendTo, subject, body);
				
				booking.setReminderSent(true);
				bookingDAO.save(booking);
			}
		}

	}

}
