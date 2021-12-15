package com.parking.booking.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class ParkingSpots {

	@Id
	@Column(name = "location_id")
	private String id;

	@Column(name = "area")
	private String area;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "latitude")
	private float latitude;
	
	@Column(name = "longitude")
	private float longitude;

	@Column(name = "district")
	private String district;

	public String getId() {
		if (id == null || id.isBlank())
			return generateId(this.area, this.pincode);
		else
			return id;
	}

	public void setId() {
		this.id = generateId(area, pincode);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ParkingSpots(String area, String pincode, float latitude, float longitude, String district) {
		super();
		this.id = generateId(area, pincode);
		this.area = area;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.district = district;
	}

	private String generateId(String area, String pincode) {
		return String.valueOf(Math.abs(this.hashCode()));
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((pincode == null) ? 0 : pincode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingSpots other = (ParkingSpots) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (pincode == null) {
			if (other.pincode != null)
				return false;
		} else if (!pincode.equals(other.pincode))
			return false;
		return true;
	}

	public ParkingSpots() {

	}

}
