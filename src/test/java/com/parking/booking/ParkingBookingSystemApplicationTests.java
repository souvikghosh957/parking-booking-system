package com.parking.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parking.booking.service.ParkingBookingService;

@SpringBootTest
class ParkingBookingSystemApplicationTests {

	@Autowired
	private ParkingBookingService parkingBookingService;
	
	

	@Test
	void contextLoads() {
	}
	

}
