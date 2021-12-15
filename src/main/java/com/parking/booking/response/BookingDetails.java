package com.parking.booking.response;

public class BookingDetails {
	private String bookingId;
	private String bookedTime;
	private String location;

	public BookingDetails(String bookingId, String bookedTime, String location) {
		super();
		this.bookingId = bookingId;
		this.bookedTime = bookedTime;
		this.location = location;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
