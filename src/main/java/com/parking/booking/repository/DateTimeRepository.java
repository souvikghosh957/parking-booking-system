package com.parking.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parking.booking.models.DateTime;

@Repository
public interface DateTimeRepository extends JpaRepository<DateTime, String>{

//	@Query(value="select * from date_time dt where dt.entry_time <= :timeFrom "
//			+ "and dt.exit_time >= :timeFrom or dt.entry_time <= :timeTo and dt.exit_time >= :timeTo "
//			+ "and location_id = :locationId", nativeQuery=true)
//	public List<DateTime> getTimeSlotIfAvailable(Date timeFrom, Date timeTo, String locationId);
	
	@Query(value="select * from date_time dt where dt.entry_time BETWEEN :timeFrom "
			+ "and :timeTo or dt.exit_time BETWEEN :timeFrom and :timeTo "
			+ "and location_id = :locationId", nativeQuery=true)
	public List<DateTime> getTimeSlotIfAvailable(Date timeFrom, Date timeTo, String locationId);
	

	

}
