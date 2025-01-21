package com.triplehelix.schedulers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.User;
import com.triplehelix.services.BookingService;
import com.triplehelix.services.EmailService;

@Component
public class FeedbackEmailScheduler {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private EmailService emailService;
	
	@Scheduled(cron = "* * * * * ?")
	public void sendFeedbackEmail() {
		List<Booking> bookings = bookingService.getBookingsForFeedback();
		
		for (Booking booking : bookings) {
			User user = booking.getUser();
			if (!booking.isFeedbackSent()) {
				String sendTo = user.getUserEmail();
				String subject = "Cascina Caccia - La tua opinione è importante!";
				String body = "Gentile " + user.getUserName() + ",\n\n"
						+ "Per favore, dicci cosa ne pensi della tua esperienza in Cascina Caccia!\n\n"
                        + "https://form.jotform.com/250082624150345\n\n"
                        + "Grazie per il tuo feedback!";

				try {
                    emailService.sendEmail(sendTo, subject, body);
                    booking.setFeedbackSent(true);
                    bookingService.saveBooking(booking);
                } catch (Exception e) {
                    System.err.println("Failed to send feedback email to: " + sendTo);
                }
			}
		}
	}

}
