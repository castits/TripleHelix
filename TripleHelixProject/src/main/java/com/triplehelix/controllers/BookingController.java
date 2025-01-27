package com.triplehelix.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplehelix.entities.Booking;
import com.triplehelix.entities.BookingStatus;
import com.triplehelix.entities.User;
import com.triplehelix.exceptions.BookingNotFoundException;
import com.triplehelix.services.BookingService;
import com.triplehelix.services.EmailService;
import com.triplehelix.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	final String DATE_FORMAT = "dd/MM/yyyy";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	@GetMapping
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Map<String, Object>>> getBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}
	
	@GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getBookingsByStatus(@RequestParam String status) {
		try {
	        BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
	        return ResponseEntity.ok(bookingService.getBookingsByStatus(bookingStatus));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(status + " is not a valid status. Allowed values are: " 
	                      + List.of(BookingStatus.values()));
	    }
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Map<String, Object>>> getBookingsByUserEmail(@RequestParam String email) {
		return ResponseEntity.ok(bookingService.getBookingsByUserEmail(email));
	}
	
	@GetMapping("/user-status")
	public ResponseEntity<?> getBookingsByUserEmailAndStatus(@RequestParam String email, @RequestParam String status) {
		try {
	        BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
	        return ResponseEntity.ok(bookingService.getBookingsByUserEmailAndStatus(email, bookingStatus));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(status + " is not a valid status. Allowed values are: "
	                      + List.of(BookingStatus.values()));
	    }
	}
	
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        User authenticatedUser = userService.getAuthenticatedUser();
        
        booking.setUser(authenticatedUser);
        booking.setStatus(BookingStatus.PENDING);
        booking.setReminderSent(false);
        booking.setFeedbackSent(false);

        Booking savedBooking = bookingService.saveBooking(booking);
        
        String imagePath = "src/main/resources/static/assets/img/deck.jpg";
		String cid = "bannerImage";
		String formattedDate = formatter.format(savedBooking.getAppointmentDate());
		
        try {
			String emailBody = "<html>" +
                    "<head>" +
                    "<style>" +
                    "  body { font-family: Arial, sans-serif; color: #333333; }" +
                    "  .email-container { margin: 0 auto; padding: 20px; max-width: 600px; border: 1px solid #dddddd; border-radius: 8px; background-color: #f9f9f9; }" +
                    "  h1 { color: #ff8400; font-size: 22px; text-align: center; }" +
                    "  p { line-height: 1.6; font-size: 16px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <h1>Richiesta di prenotazione</h1>" +
                    "    <p><strong>Nome:</strong> " + savedBooking.getUser().getUserName() + " " + savedBooking.getUser().getUserSurname() + "</p>" +
                    "    <p><strong>Email:</strong> " + savedBooking.getUser().getUserEmail() + "</p>" +
                    "    <p><strong>Istituto:</strong> " + savedBooking.getInstitute() + "</p>" +
                    "    <p><strong>Numero Partecipanti:</strong> " + savedBooking.getParticipantQuantity() + "</p>" +
                    "    <p><strong>Data Richiesta:</strong> " + formattedDate + "</p>" +
                    "    <p><strong>Fascia Oraria:</strong> " + savedBooking.getTimeSlot() + "</p>" +
                    "    <p><strong>Attività:</strong> " + savedBooking.getActivity() + "</p>" +
                    "    <p><strong>Messaggio:</strong></p>" +
                    "    <p>" + (savedBooking.getBookingInfoReq() != "" ? savedBooking.getBookingInfoReq() : "N/D") + "</p>" +
                    "    <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			emailService.sendEmail("triplehelixtest1@gmail.com",
					"Richiesta di prenotazione da parte di " + savedBooking.getUser().getUserName() + " " + savedBooking.getUser().getUserSurname(),
					emailBody,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send request email from " + booking.getUser().getUserEmail());
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
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-container'>" +
                    "    <h1>Grazie per la tua prenotazione!</h1>" +
                    "    <p>Gentile <strong>" + savedBooking.getUser().getUserName() + " " + savedBooking.getUser().getUserSurname() + "</strong>,</p>" +
                    "    <p>Grazie per aver richiesto una prenotazione presto la nostra struttura. Abbiamo ricevuto la tua richiesta e il nostro team ti contatterà al più presto.</p>" +
                    "    <p>Nel frattempo, se hai bisogno di aggiungere informazioni o hai altre domande, sentiti libero di rispondere a questa email.</p>" +
                    "    <p>Siamo qui per aiutarti!</p>" +
                    "    <div class='footer'>" +
                    "      <p>Con i migliori saluti,</p>" +
                    "      <p><strong>Il Team di Cascina Caccia</strong></p>" +
                    "      <img src='cid:" + cid + "' alt='Banner' style='width:100%; border-radius: 8px 8px 0 0;' />" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";

			emailService.sendEmail(savedBooking.getUser().getUserEmail(),
					"Grazie per la tua prenotazione",
					responseEmail,
					imagePath,
					cid);
		} catch (Exception e) {
			System.err.println("Failed to send request email from " + booking.getUser().getUserEmail());
		}
        
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }
    
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBooking(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, updates);
            return ResponseEntity.ok(updatedBooking);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeBookingStatus(@PathVariable int id, @RequestParam String status) {
        try {
            bookingService.changeBookingStatus(id, status);
            return ResponseEntity.ok("Status updated successfully");
        } catch (BookingNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBookingById(@PathVariable int id) {
        try {
            bookingService.deleteBookingById(id);
            return ResponseEntity.ok("Booking deleted successfully");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
