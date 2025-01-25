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
				String cid = "bannerImage";
				String subject = "Cascina Caccia - La tua opinione è importante!";
				String body = "<html>" +
			              "<head>" +
			              "<style>" +
			              "  body { font-family: Arial, sans-serif; color: #333333; }" +
			              "  .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; }" +
			              "  h1 { color: #ff8400; font-size: 24px; text-align: center; }" +
			              "  p { line-height: 1.6; font-size: 16px; }" +
			              "  a { color: #ff8400; text-decoration: none; font-weight: bold; }" +
			              "  .cta-button { display: block; margin: 20px auto; text-align: center; background-color: #ff8400; color: white !important; text-decoration: none; padding: 10px 20px; border-radius: 5px; font-size: 18px; font-weight: bold; width: fit-content; }" +
			              "  .footer { margin-top: 20px; font-size: 14px; color: #777777; }" +
			              "</style>" +
			              "</head>" +
			              "<body>" +
			              "  <div class='email-container'>" +
			              "    <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
			              "    <h1>La tua opinione è importante!</h1>" +
			              "    <p>Gentile <strong>" + user.getUserName() + "</strong>,</p>" +
			              "    <p>Grazie per essere stato nostro ospite presso Cascina Caccia. Vorremmo sapere cosa ne pensi della tua esperienza. Il tuo feedback è fondamentale per aiutarci a migliorare i nostri servizi.</p>" +
			              "    <a class='cta-button' href='https://form.jotform.com/250082624150345'>Lascia il tuo feedback</a>" +
			              "    <p>Grazie per il tempo che ci dedicherai!</p>" +
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
                    booking.setFeedbackSent(true);
                    bookingService.saveBooking(booking);
                } catch (Exception e) {
                    System.err.println("Failed to send feedback email to " + sendTo);
                }
			}
		}
	}

}
