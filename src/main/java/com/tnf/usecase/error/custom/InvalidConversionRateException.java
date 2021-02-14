package com.tnf.usecase.error.custom;

public class InvalidConversionRateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1927285725878068614L;

	public InvalidConversionRateException(String curency) {
		super("Invalid currency rate recived for currency: "+curency);
	}
}
