package com.parking.booking.exceptions;

public class TimeSlotNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TimeSlotNotAvailableException(String errorMessage) {
		super(errorMessage);
	}
}
