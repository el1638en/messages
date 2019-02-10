package com.syscom.exceptions;

/**
 * Gestion des exceptions techniques
 *
 */
public class TechnicalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	/**
	 * Constructeur.
	 *
	 * @param message of exception.
	 */
	public TechnicalException(final String message) {
		super(message);
		this.message = message;
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur.
	 *
	 * @param cause of exception.
	 */
	public TechnicalException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}

}
