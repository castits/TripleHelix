package com.triplehelix.exceptions;

/**
 * Custom exception for handling cases where a booking is not found
 * Extends the RuntimeException to enable unchecked exception handling
 */
public class BookingNotFoundException extends RuntimeException {
	
    /**
     * Constructor that accepts a custom error message
     * This message is passed to the parent RuntimeException class
     * @param message - the error message that describes the exception
     */
	public BookingNotFoundException(String message) {
        super(message);
    }

}
