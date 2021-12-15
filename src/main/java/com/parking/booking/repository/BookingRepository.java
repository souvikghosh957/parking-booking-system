package com.parking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.booking.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

}
