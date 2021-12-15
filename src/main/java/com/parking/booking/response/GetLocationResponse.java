package com.parking.booking.response;

import java.util.List;

import com.parking.booking.models.ParkingSpots;

public class GetLocationResponse {

	private String message;
	private ParkingSpots parkingSpots;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ParkingSpots getParkingSpots() {
		return parkingSpots;
	}

	public void setParkingSpots(ParkingSpots parkingSpots) {
		this.parkingSpots = parkingSpots;
	}

	public GetLocationResponse(String message, ParkingSpots parkingSpots) {
		super();
		this.message = message;
		this.parkingSpots = parkingSpots;
	}
	
	public GetLocationResponse() {
		
	}
}
