package com.parking.booking.response;

import java.util.List;

import com.parking.booking.models.ParkingSpots;

public class AllLocationsResponse {
	private String message;
	private List<ParkingSpots> parkingSpots;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ParkingSpots> getParkingSpots() {
		return parkingSpots;
	}

	public void setParkingSpots(List<ParkingSpots> parkingSpots) {
		this.parkingSpots = parkingSpots;
	}

	public AllLocationsResponse(String message, List<ParkingSpots> parkingSpots) {
		super();
		this.message = message;
		this.parkingSpots = parkingSpots;
	}
}
