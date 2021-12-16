package com.parking.booking;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ParkingBookingSystemApplication {

	/**
	 *  Parking booking system. The starting point of the application.
	 * @param args
	 * @author Souvik Ghosh
	 */
	public static void main(String[] args) {
		SpringApplication.run(ParkingBookingSystemApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(
				Duration.ofMinutes(2)).setReadTimeout(
						Duration.ofMinutes(2)).build();
	}

}
