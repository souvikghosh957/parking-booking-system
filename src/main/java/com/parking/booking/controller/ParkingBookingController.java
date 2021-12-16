package com.parking.booking.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.booking.exceptions.InvalidTimeSlotException;
import com.parking.booking.exceptions.TimeSlotNotAvailableException;
import com.parking.booking.models.ParkingSpots;
import com.parking.booking.request.LocationRequest;
import com.parking.booking.request.TicketRequest;
import com.parking.booking.response.AddLocationResponse;
import com.parking.booking.response.AllLocationsResponse;
import com.parking.booking.response.BookingDetails;
import com.parking.booking.response.BookingResponse;
import com.parking.booking.response.DeletedLocationResponse;
import com.parking.booking.response.GetLocationResponse;
import com.parking.booking.response.UpdateLocationResponse;
import com.parking.booking.service.ParkingBookingService;

@RestController
@RequestMapping("/parking")
public class ParkingBookingController {
	private Logger logger = LoggerFactory.getLogger(ParkingBookingController.class);

	@Autowired
	private ParkingBookingService parkingBookingService;

	/**
	 * This end point allows admin to add parking spot. For getting the
	 * city/district external Post Office API is called from this system.
	 * 
	 * @param locationRequest
	 * @return Response contains generated locationId and added location name.
	 */
	@PostMapping(value = "/admin/addParkingLocations", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AddLocationResponse> addParkingLocations(@RequestBody List<LocationRequest> locationRequest) {
		String message = "";
		if (!locationRequest.isEmpty()) {
			try {
				Map<String, String> generatedIdAreaMap = parkingBookingService.addParkingLocations(locationRequest);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new AddLocationResponse("Below locations added successfully.", generatedIdAreaMap));

			} catch (Exception ex) {
				logger.error("Exception occured while adding locations: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		message = "Please provide non empty requests!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new AddLocationResponse(message, Collections.emptyMap()));
	}

	/**
	 * With this endpont any user can get all available locations or all available
	 * location present in certain district (like Bengaluru)
	 * 
	 * @param district
	 * @return all available parking location
	 */
	@GetMapping(value = "/getAllLocations", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AllLocationsResponse> getParkingLocations(
			@RequestParam(name = "district", required = false) String district) {
		String message = "";
		List<ParkingSpots> parkingSpots = null;
		try {
			if (district != null && !district.isBlank()) {
				parkingSpots = parkingBookingService.getParkingLocationsByDistrict(district);
				message = "All available location in district " + district;
				return ResponseEntity.status(HttpStatus.OK).body(new AllLocationsResponse(message, parkingSpots));
			} else {
				parkingSpots = parkingBookingService.getAllParkingLocations();
				message = "All available locations";
				return ResponseEntity.status(HttpStatus.OK).body(new AllLocationsResponse(message, parkingSpots));
			}
		} catch (Exception e) {
			message = "Could not fetch the location details.";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new AllLocationsResponse(message, parkingSpots));
		}
	}

	/**
	 * With this any user can get a parking location by name/pincode or the location
	 * id.
	 * 
	 * @param areaName
	 * @param pincode
	 * @param locationId
	 * @return Fetched area
	 */
	@GetMapping(value = "/getParkingLocation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GetLocationResponse> getParkingLocation(
			@RequestParam(name = "areaName", required = false) String areaName,
			@RequestParam(name = "pincode", required = false) String pincode,
			@RequestParam(name = "locationId", required = false) String locationId) {
		String message = "";
		ParkingSpots parkingSpots = null;
		try {
			if ((areaName != null && !areaName.isEmpty()) || (pincode != null && !pincode.isEmpty())
					|| (locationId != null && !locationId.isEmpty())) {
				parkingSpots = parkingBookingService.getParkingLocation(areaName, pincode, locationId);
				if (parkingSpots != null) {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new GetLocationResponse("Find detailed location below: ", parkingSpots));
				} else {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new GetLocationResponse("No record found for the requested location.", parkingSpots));
				}
			} else {
				message = "Please enter a valid location id, area name or pin code.";
				return ResponseEntity.status(HttpStatus.OK).body(new GetLocationResponse(message, parkingSpots));
			}
		} catch (Exception e) {
			message = "Could not fetch the location details.";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new GetLocationResponse(message, parkingSpots));
		}
	}

	/**
	 * An admin can update the parking location by passing location request. Area
	 * name or pincode should not be changed while updating the location details.
	 * 
	 * @param locationRequest
	 * @return
	 */
	@PutMapping(value = "/admin/updateParkingLocation", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UpdateLocationResponse> updateParkingLocation(@RequestBody LocationRequest locationRequest) {
		String message = "";
		try {
			if (locationRequest != null) {
				Map<String, String> idAreaMap = parkingBookingService.updateParkingLocation(locationRequest);
				if (idAreaMap != null) {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new UpdateLocationResponse("Location updated successfully.", idAreaMap));
				} else {
					return ResponseEntity.status(HttpStatus.OK).body(new UpdateLocationResponse(
							"Location was not found. Check if area and pincode is correct", idAreaMap));
				}
			} else {
				message = "Please enter a valid llocation details.";
				return ResponseEntity.status(HttpStatus.OK).body(new UpdateLocationResponse(message, null));
			}
		} catch (Exception e) {
			message = "Error occured. Could not update the location details.";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UpdateLocationResponse(message, null));
		}
	}

	/**
	 * An admin can remove a location by passing area name in the url.
	 * 
	 * @param areaName
	 * @return success message with location Id and location name.
	 */
	@DeleteMapping(value = "/admin/removeLocations/{areaName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DeletedLocationResponse> removeLocations(@PathVariable String areaName) {
		String message = "";
		if (areaName != null && !areaName.isEmpty()) {
			try {
				Map<String, String> deletedIdAreaMap = parkingBookingService.removeParkingLocations(areaName);
				if (deletedIdAreaMap != null && !deletedIdAreaMap.isEmpty()) {
					return ResponseEntity.status(HttpStatus.OK).body(
							new DeletedLocationResponse("Below location is deleted successfully.", deletedIdAreaMap));
				} else {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new DeletedLocationResponse("Location was not found", deletedIdAreaMap));
				}

			} catch (Exception ex) {
				logger.error("Exception occured while removing locations: " + ex.getMessage());
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new DeletedLocationResponse("Exception occured while deleting locations", null));
			}
		}
		message = "Please provide non empty requests!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new DeletedLocationResponse(message, Collections.emptyMap()));
	}

	/**
	 * An user can book a ticket through this API. In request we can pass location
	 * name entry and exit time, if that time will be recurring on daily, weekly or
	 * monthly basis and for number of time it would repeat. If the booking spot is
	 * already booked on the requested time we can not book and get response
	 * accordingly. If the location is not available we can not book that location
	 * and get the response. Time entered should be current or any future time and 
	 * chronology should be correct.
	 * 
	 * @param ticketRequest
	 * @return Booked id, location name and time for which it's booked for the user.
	 */
	@PostMapping(value = "/bookTicket", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookingResponse> bookTicket(@RequestBody TicketRequest ticketRequest) {
		String message = "";
		if (ticketRequest != null) {
			try {
				List<BookingDetails> bookingDetails = parkingBookingService.bookTicket(ticketRequest);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new BookingResponse("Your ticket has been booked! ", bookingDetails));
			} catch (TimeSlotNotAvailableException ex) {
				logger.error(ex.getMessage());
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new BookingResponse(
						"The entered time slot is not available. Choose other time for this location.", null));
			} catch (InvalidTimeSlotException ex) {
				message = "InvalidTimeSlotException occured while adding locations: ";
				logger.error(ex.getMessage());
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new BookingResponse(ex.getMessage(), null));
			} catch (Exception ex) {
				message = "Exception occured while booking ticket ";
				logger.error(ex.getMessage());
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new BookingResponse(ex.getMessage(), null));
			}
		}
		message = "Please provide non empty requests!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BookingResponse(message, null));
	}

}
