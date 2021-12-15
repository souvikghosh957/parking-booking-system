package com.parking.booking.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PinCodeResponse {
	@JsonProperty(value = "PostOffice")
	private List<PostOffice> postOffices;

	public List<PostOffice> getPostOffices() {
		return postOffices;
	}

	public void setPostOffices(List<PostOffice> postOffices) {
		this.postOffices = postOffices;
	}	
}
