package com.parking.booking.response;

import java.util.Map;

public class UpdateLocationResponse {
	private String message;
	private Map<String, String> idAreaMap;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getIdAreaMap() {
		return idAreaMap;
	}
	public void setIdAreaMap(Map<String, String> idAreaMap) {
		this.idAreaMap = idAreaMap;
	}
	
	public UpdateLocationResponse(String message, Map<String, String> idAreaMap) {
		super();
		this.message = message;
		this.idAreaMap = idAreaMap;
	}
	
	public UpdateLocationResponse() {
		
	}
}
