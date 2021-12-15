package com.parking.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parking.booking.models.ParkingSpots;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpots, String> {
	
	@Query(value="select * from location l where l.location_id= :locationId", nativeQuery=true)
	public ParkingSpots getLocationId(String locationId);

	public ParkingSpots findByArea(String areaName);

	public ParkingSpots findByPincode(String pincode);
	
	public List<ParkingSpots> findByDistrict(String district);

}
