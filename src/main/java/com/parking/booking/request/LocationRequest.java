package com.parking.booking.request;

public class LocationRequest {		
	private String area;
	private String pincode;
	private String latitude;
	private String longitude;
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
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public LocationRequest(String area, String pincode, String latitude, String longitude) {
		super();
		this.area = area;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LocationRequest() {
		
	}
	
}
