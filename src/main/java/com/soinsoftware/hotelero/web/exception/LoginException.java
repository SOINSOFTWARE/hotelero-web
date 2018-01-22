package com.soinsoftware.hotelero.web.exception;

/**
 * 
 * @author Carlos Rodriguez
 * @since 1.0.0
 *
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 6207596019507777997L;

	public LoginException(final String message) {
		super(message);
	}
}