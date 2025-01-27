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
	
	// Dependency injection for the booking service
	@Autowired
	private BookingService bookingService;
	
	// Dependency injection for the email service
	@Autowired
	private EmailService emailService;
	
	// Dependency injection for the booking repository
	@Autowired
	private BookingDAO bookingDAO;
	
	// Date format to be used in emails
	final String DATE_FORMAT = "dd/MM/yyyy";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
    /**
     * Scheduled method to send booking reminders
     * Runs based on the cron expression defined (in this case every second)
     */
	@Scheduled(cron = "* * * * * ?")
	public void sendBookingsReminders() throws MessagingException {
		// Get all bookings that are eligible for reminders
		List<Booking> bookings = bookingService.getBookingsForReminder();
		
		// Iterate through all the bookings to process reminders
		for (Booking booking : bookings) {
			// Check if the reminder has already been sent for this booking
			if (!booking.isReminderSent()) {
                // Get the email address of the user associated with the booking
				String sendTo = booking.getUser().getUserEmail();
				
				// Prepare the email
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
			              "    <h1>Promemoria per la tua visita</h1>" +
			              "    <p>Gentile <strong>" + booking.getUser().getUserName() + "</strong>,</p>" +
			              "    <p>Ti ricordiamo che hai una visita presso la nostra struttura programmata per il: <strong>" + formattedDate + "</strong></p>" +
			              "    <p>Ti aspettiamo con piacere e siamo a disposizione per qualsiasi domanda o necessità. Non esitare a contattarci se hai bisogno di ulteriori informazioni.</p>" +
			              "    <p>Grazie per aver scelto la nostra struttura!</p>" +
			              "    <div class='footer'>" +
			              "      <p>Con i migliori saluti,</p>" +
			              "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
			              "      <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
			              "    </div>" +
			              "  </div>" +
			              "</body>" +
			              "</html>";
				
				try {
					String imagePath = "src/main/resources/static/assets/img/deck.jpg";
					emailService.sendEmail(sendTo, subject, body, imagePath, cid); // Send the email
					booking.setReminderSent(true); // Set the booking reminderSent to true
					bookingDAO.save(booking); // Save the updated booking
				} catch (Exception e) {
					System.err.println("Failed to send reminder email to " + sendTo); // Print an error if the email can't be sent
				}
			}
		}

	}

}
