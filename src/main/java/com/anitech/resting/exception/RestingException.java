package com.anitech.resting.exception;

/**
 * Custom exception for Resting library
 * 
 * @author Tapas
 *
 */
public class RestingException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestingException() {
		super();
	}
	
	public RestingException(String message) {
		super(message);
	}
	
	public RestingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RestingException(Throwable cause) {
		super(cause);
	}
	
}
