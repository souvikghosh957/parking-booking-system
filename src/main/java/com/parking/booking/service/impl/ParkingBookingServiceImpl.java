package com.parking.booking.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.booking.exceptions.AreaNotFoundException;
import com.parking.booking.exceptions.InvalidTimeSlotException;
import com.parking.booking.exceptions.TimeSlotNotAvailableException;
import com.parking.booking.models.Booking;
import com.parking.booking.models.DateTime;
import com.parking.booking.models.ParkingSpots;
import com.parking.booking.models.PinCodeResponse;
import com.parking.booking.repository.BookingRepository;
import com.parking.booking.repository.DateTimeRepository;
import com.parking.booking.repository.ParkingSpotRepository;
import com.parking.booking.request.LocationRequest;
import com.parking.booking.request.TicketRequest;
import com.parking.booking.response.BookingDetails;
import com.parking.booking.service.ParkingBookingService;

@Service
@Transactional
public class ParkingBookingServiceImpl implements ParkingBookingService {

	private static String govtPostalApi = "https://api.postalpincode.in/pincode/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ParkingSpotRepository parkingSpotRepository;

	@Autowired
	private DateTimeRepository dateTimeRepository;

	@Autowired
	private BookingRepository bookingRepository;

	public Map<String, String> addParkingLocations(List<LocationRequest> locationRequests) {
		List<ParkingSpots> addedParkingSpots = parkingSpotRepository.saveAll(prepareParkingSpots(locationRequests));
		Map<String, String> generatedIdAreaMap = addedParkingSpots.stream()
				.collect(Collectors.toMap(ParkingSpots::getId, ParkingSpots::getArea));
		return generatedIdAreaMap;
	}

	private List<ParkingSpots> prepareParkingSpots(List<LocationRequest> locationRequests) {
		List<ParkingSpots> parkingSpots = new ArrayList<ParkingSpots>();
		for (LocationRequest locationRequest : locationRequests) {
			ParkingSpots ps = new ParkingSpots();
			ps.setArea(locationRequest.getArea());
			ps.setLatitude(Float.valueOf(locationRequest.getLatitude()));
			ps.setLongitude(Float.valueOf(locationRequest.getLongitude()));
			String pincode = locationRequest.getPincode().replaceAll("\\s+", "");
			PinCodeResponse pinCodeResponse = getDistrictDetailsSc(pincode);
			String district = "";
			if (pinCodeResponse != null && pinCodeResponse.getPostOffices() != null
					&& !pinCodeResponse.getPostOffices().isEmpty()) {
				district = (pinCodeResponse.getPostOffices()).get(0).getDistrict();
			}
			ps.setPincode(pincode);
			ps.setDistrict(district);
			ps.setId();
			parkingSpots.add(ps);
		}
		return parkingSpots;
	}

	public PinCodeResponse getDistrictDetailsSc(String pincode) {
		String postalRequestUrl = govtPostalApi + pincode;
		try {
			String responseString = restTemplate.getForObject(postalRequestUrl, String.class);
			ObjectMapper om = new ObjectMapper();
			PinCodeResponse pinCodeResponse = om.readValue(responseString.substring(1, responseString.length() - 1),
					PinCodeResponse.class);
			return pinCodeResponse;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public ParkingSpots getParkingLocation(String areaName, String pincode, String locationId) {
		if (locationId != null && !locationId.isBlank()) {
			return parkingSpotRepository.getLocationId(locationId);
		} else if (areaName != null && !areaName.isEmpty()) {
			return parkingSpotRepository.findByArea(areaName);
		} else if (pincode != null && !pincode.isEmpty()) {
			return parkingSpotRepository.findByPincode(pincode);
		}
		return null;
	}

	public List<ParkingSpots> getAllParkingLocations() {
		return parkingSpotRepository.findAll();
	}

	public List<ParkingSpots> getParkingLocationsByDistrict(String district) {
		return parkingSpotRepository.findByDistrict(district);
	}

	public Map<String, String> updateParkingLocation(LocationRequest locationRequest) {
		List<LocationRequest> locationRequests = new ArrayList<LocationRequest>();
		locationRequests.add(locationRequest);
		List<ParkingSpots> listParkingSpot = prepareParkingSpots(locationRequests);
		if (!listParkingSpot.isEmpty() && checkIfLocationExists(listParkingSpot.get(0))) {
			ParkingSpots updatedParkingSpots = parkingSpotRepository.save(listParkingSpot.get(0));
			String key = updatedParkingSpots.getId();
			String value = updatedParkingSpots.getArea();
			Map<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			return map;
		} else {
			return null;
		}
	}

	private boolean checkIfLocationExists(ParkingSpots parkingSpots) {
		ParkingSpots ps = parkingSpotRepository.getLocationId(parkingSpots.getId());
		return ps != null && ps.getArea() != null && !ps.getArea().isBlank();
	}

	public Map<String, String> removeParkingLocations(String areaName) {
		ParkingSpots parkingSpot = parkingSpotRepository.findByArea(areaName);
		if (parkingSpot != null) {
			Map<String, String> map = new HashMap<String, String>();
			parkingSpotRepository.delete(parkingSpot);
			String key = parkingSpot.getId();
			String value = parkingSpot.getArea();
			map.put(key, value);
			return map;
		}
		return null;
	}

	public List<BookingDetails> bookTicket(TicketRequest ticketRequest) throws Exception {
		String userId = getUserNameOfLoggedInUser();
		if (userId == null || userId.isBlank()) {
			throw new Exception("Could not retrive the userId");
		}
		if (checkIfTimesValid(ticketRequest.getBookFrom(), ticketRequest.getBookTo())) {
			throw new InvalidTimeSlotException("Entered times are either past time or not in correct chronology.");
		}
		List<BookingDetails> bookings = new ArrayList<BookingDetails>();
		List<DateTime> bookedDateTimes = bookTimeIfAvailable(ticketRequest);
		for (DateTime bookedDateTime : bookedDateTimes) {
			Booking booking = bookingRepository
					.save(new Booking(bookedDateTime.getLocationId(), userId, bookedDateTime.getId(), true));
			String areaName = parkingSpotRepository.getById(booking.getLocationId()).getArea();
			BookingDetails bd = new BookingDetails(String.valueOf(booking.getId()),
					bookedDateTime.getBookedFrom() + " to " + bookedDateTime.getBookedTo(), areaName);
			bookings.add(bd);
		}
		return bookings;
	}

	private String getUserNameOfLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

	private boolean checkIfTimesValid(Date bookFrom, Date bookTo) {
		Instant currentTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant();
		return !(bookFrom.toInstant().isAfter(currentTime) && bookTo.toInstant().isAfter(currentTime)
				&& bookTo.toInstant().isAfter(bookFrom.toInstant()));
	}

	private List<DateTime> bookTimeIfAvailable(TicketRequest ticketRequest) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		List<DateTime> dateTime = new ArrayList<DateTime>();
		Date timeFrom = ticketRequest.getBookFrom();
		Date timeTo = ticketRequest.getBookTo();
		ParkingSpots parkingSpot = parkingSpotRepository.getByAreaName(ticketRequest.getParkingArea());
		if (parkingSpot == null) {
			throw new AreaNotFoundException("Entered booking area is not available.");
		}
		String locationId = parkingSpot.getId();
		List<DateTime> dateTimeSlot = dateTimeRepository.getTimeSlotIfAvailable(timeFrom, timeTo, locationId);
		if (checkIfTimeSlotAvailable(dateTimeSlot)) {
			dateTime.add(dateTimeRepository.save(new DateTime(timeFrom, timeTo, locationId, true)));
		} else {
			throw new TimeSlotNotAvailableException(
					"Time slot is not available. timeFrom = " + timeFrom + ", timeTo" + timeTo);
		}

		if (ticketRequest.isDailyRecurring() || ticketRequest.isMonthlyRecurring()
				|| ticketRequest.isWeeklyRecurring()) {
			dateTime.addAll(bookRecurringParkingTimes(ticketRequest, timeFrom, timeTo, locationId));
		}
		return dateTime;

	}

	private List<DateTime> bookRecurringParkingTimes(TicketRequest ticketRequest, Date timeFrom, Date timeTo,
			String locationId) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		List<DateTime> dateTime = new ArrayList<DateTime>();
		if (ticketRequest.isDailyRecurring()) {
			for (int i = 1; i <= ticketRequest.getNumberOfDays() + 1; i++) {
				Date newTimeFrom = Date.from(timeFrom.toInstant().plus(i, ChronoUnit.DAYS));
				Date newTimeTo = Date.from(timeTo.toInstant().plus(i, ChronoUnit.DAYS));
				List<DateTime> dateTimeSlot = dateTimeRepository.getTimeSlotIfAvailable(newTimeFrom, newTimeTo,
						locationId);
				if (checkIfTimeSlotAvailable(dateTimeSlot)) {
					dateTime.add(dateTimeRepository.save(new DateTime(newTimeFrom, newTimeTo, locationId, true)));
				}
			}
		} else if (ticketRequest.isMonthlyRecurring()) {
			for (int i = 1; i <= ticketRequest.getNumberOfDays(); i++) {
				Date newTimeFrom = Date.from(timeFrom.toInstant().plus(30 * i, ChronoUnit.DAYS));
				Date newTimeTo = Date.from(timeTo.toInstant().plus(30 * i, ChronoUnit.DAYS));
				List<DateTime> dateTimeSlot = dateTimeRepository.getTimeSlotIfAvailable(newTimeFrom, newTimeTo,
						locationId);
				if (checkIfTimeSlotAvailable(dateTimeSlot)) {
					dateTime.add(dateTimeRepository.save(new DateTime(newTimeFrom, newTimeTo, locationId, true)));
				}
			}
		} else if (ticketRequest.isWeeklyRecurring()) {
			for (int i = 1; i <= ticketRequest.getNumberOfDays(); i++) {
				Date newTimeFrom = Date.from(timeFrom.toInstant().plus(7 * i, ChronoUnit.DAYS));
				Date newTimeTo = Date.from(timeTo.toInstant().plus(7 * i, ChronoUnit.DAYS));
				List<DateTime> dateTimeSlot = dateTimeRepository.getTimeSlotIfAvailable(newTimeFrom, newTimeTo,
						locationId);
				if (checkIfTimeSlotAvailable(dateTimeSlot)) {
					dateTime.add(dateTimeRepository.save(new DateTime(newTimeFrom, newTimeTo, locationId, true)));
				}
			}
		}

		return dateTime;
	}

	private boolean checkIfTimeSlotAvailable(List<DateTime> dateTimeSlot) {
		return dateTimeSlot == null || dateTimeSlot.isEmpty() || (dateTimeSlot != null && !dateTimeSlot.isEmpty()
				&& dateTimeSlot.stream().filter(it -> it.getIsActive()).collect(Collectors.toList()).isEmpty());
	}

}
