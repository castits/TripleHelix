package com.triplehelix.exceptions;

/**
 * Custom exception for handling cases where a feedback is not found
 * Extends the RuntimeException to enable unchecked exception handling
 */
public class FeedbackNotFoundException extends RuntimeException {
	
    /**
     * Constructor that accepts a custom error message
     * This message is passed to the parent RuntimeException class
     * @param message - the error message that describes the exception
     */
	public FeedbackNotFoundException(String message) {
        super(message);
    }

}
