package com.parking.booking.models;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "date_time")
public class DateTime {

	@Id
	@Column(name = "date_time_id")
	private String id;

	@Column(name = "entry_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookedFrom;

	@Column(name = "exit_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookedTo;

	@Column(name = "location_id")
	private String locationId;

	@Column(name = "is_active")
	private boolean isActive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id == null) {
			this.id = generateRandomBookingId();
		} else {
			this.id = id;
		}
	}

	public Date getBookedFrom() {
		return bookedFrom;
	}

	public void setBookedFrom(Date bookedFrom) {
		this.bookedFrom = bookedFrom;
	}

	public Date getBookedTo() {
		return bookedTo;
	}

	public void setBookedTo(Date bookedTo) {
		this.bookedTo = bookedTo;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public DateTime(Date bookedFrom, Date bookedTo, String locationId, boolean isActive) {
		super();
		this.id = generateRandomBookingId();
		this.bookedFrom = bookedFrom;
		this.bookedTo = bookedTo;
		this.locationId = locationId;
		this.isActive = isActive;
	}

	public DateTime() {

	}
	
	private String generateRandomBookingId() {
		int leftLimit = 97; 
		int rightLimit = 122;
		int targetStringLength = 10;
		Random random = new Random();
		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}
}
