package com.parking.booking.service;

import java.util.List;
import java.util.Map;

import com.parking.booking.models.ParkingSpots;
import com.parking.booking.request.LocationRequest;
import com.parking.booking.request.TicketRequest;
import com.parking.booking.response.BookingDetails;

public interface ParkingBookingService {

	public Map<String, String> addParkingLocations(List<LocationRequest> locationRequests);

	public List<ParkingSpots> getParkingLocationsByDistrict(String district);

	public ParkingSpots getParkingLocation(String areaName, String pincode, String locationId);

	public Map<String, String> updateParkingLocation(LocationRequest locationRequest);

	public Map<String, String> removeParkingLocations(String areaName);

	public List<BookingDetails> bookTicket(TicketRequest ticketRequest) throws Exception;
	
	public List<ParkingSpots> getAllParkingLocations();
}
