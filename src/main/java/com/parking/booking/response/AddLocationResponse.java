package com.parking.booking.response;

import java.util.Map;

public class AddLocationResponse {
	private String message;
	private Map<String, String> generatedIdAreaMap;

	public AddLocationResponse() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getUpdatedIdAreaMap() {
		return generatedIdAreaMap;
	}

	public void setUpdatedIdAreaMap(Map<String, String> updatedIdAreaMap) {
		this.generatedIdAreaMap = updatedIdAreaMap;
	}

	public AddLocationResponse(String message, Map<String, String> updatedIdAreaMap) {
		super();
		this.message = message;
		this.generatedIdAreaMap = updatedIdAreaMap;
	}
}
