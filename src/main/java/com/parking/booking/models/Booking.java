package com.parking.booking.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "my_entity_seq_gen")
	@SequenceGenerator(name = "my_entity_seq_gen", sequenceName = "MY_ENTITY_SEQ")
	@Column(name = "booking_id")
	private int id;
	@Column(name = "location_id")
	private String locationId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "time_id")
	private String timeId;
	@Column(name = "is_active")
	private boolean isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Booking( String locationId, String userId, String timeId, boolean isActive) {
		super();
		this.locationId = locationId;
		this.userId = userId;
		this.timeId = timeId;
		this.isActive = isActive;
	}

	public Booking() {

	}

}
