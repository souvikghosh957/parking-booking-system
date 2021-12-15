package com.parking.booking.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponse {
	@JsonProperty
	private String message;

	@JsonProperty
	private List<BookingDetails> bookingDetails;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(List<BookingDetails> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public BookingResponse(String message, List<BookingDetails> bookingDetails) {
		super();
		this.message = message;
		this.bookingDetails = bookingDetails;
	}

}
