package com.tnf.usecase.error.custom;

public class IncompleteConversionMapException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3435740772606501416L;

	public IncompleteConversionMapException(String curency) {
		super("Currency rate not recived for currency: "+curency);
	}
}
