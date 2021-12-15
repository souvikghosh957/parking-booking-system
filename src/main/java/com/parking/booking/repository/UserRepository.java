package com.parking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parking.booking.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	@Query(value="select * from users u where u.user_id= :userId", nativeQuery=true)
	public User getByUserId(String userId);

}
