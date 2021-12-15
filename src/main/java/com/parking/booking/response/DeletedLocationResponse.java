package com.parking.booking.response;

import java.util.Map;

public class DeletedLocationResponse {

	private String message;

	private Map<String, String> idAreaMap;

	public DeletedLocationResponse(String message, Map<String, String> idAreaMap) {
		super();
		this.message = message;
		this.idAreaMap = idAreaMap;
	}

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
}
