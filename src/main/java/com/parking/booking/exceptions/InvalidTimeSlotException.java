package com.parking.booking.exceptions;

public class InvalidTimeSlotException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTimeSlotException(String errorMessage) {
		super(errorMessage);
	}
}
