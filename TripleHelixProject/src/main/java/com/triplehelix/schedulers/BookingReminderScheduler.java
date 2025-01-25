package com.triplehelix.schedulers;

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
				String cid = "bannerImage";
				String formattedDate = formatter.format(booking.getAppointmentDate());
				String subject = "Promemoria Visita del " + formattedDate;
				String body = "<html>" +
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
			              "    <h1>Promemoria per la tua visita</h1>" +
			              "    <p>Gentile <strong>" + booking.getUser().getUserName() + "</strong>,</p>" +
			              "    <p>Ti ricordiamo che hai una visita presso la nostra struttura programmata per il: <strong>" + formattedDate + "</strong></p>" +
			              "    <p>Ti aspettiamo con piacere e siamo a disposizione per qualsiasi domanda o necessità. Non esitare a contattarci se hai bisogno di ulteriori informazioni.</p>" +
			              "    <p>Grazie per aver scelto la nostra struttura!</p>" +
			              "    <div class='footer'>" +
			              "      <p>Con i migliori saluti,</p>" +
			              "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
			              "    </div>" +
			              "  </div>" +
			              "</body>" +
			              "</html>";
				
				try {
					String imagePath = "src/main/resources/static/assets/img/deck.jpg";
					emailService.sendEmail(sendTo, subject, body, imagePath, cid);
					booking.setReminderSent(true);
					bookingDAO.save(booking);
				} catch (Exception e) {
					System.err.println("Failed to send reminder email to " + sendTo);
				}
			}
		}

	}

}
