package com.parking.booking.exceptions;

public class AreaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AreaNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
