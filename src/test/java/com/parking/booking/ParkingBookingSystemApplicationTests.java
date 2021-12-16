package com.parking.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parking.booking.service.impl.ParkingBookingServiceImpl;

@SpringBootTest
class ParkingBookingSystemApplicationTests {

	@Autowired
	private ParkingBookingServiceImpl parkingBookingService;
	
	

	@Test
	void contextLoads() {
	}
	

}
