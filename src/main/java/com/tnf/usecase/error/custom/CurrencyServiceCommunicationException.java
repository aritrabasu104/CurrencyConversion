package com.tnf.usecase.error.custom;

public class CurrencyServiceCommunicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8678263324818008806L;

	public CurrencyServiceCommunicationException(String message) {
		super("Communication with currency service failed: " + message);
	}
}
