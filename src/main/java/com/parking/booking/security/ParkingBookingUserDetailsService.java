package com.parking.booking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parking.booking.models.User;
import com.parking.booking.repository.UserRepository;

@Service
public class ParkingBookingUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userRepo.getByUserId(username);
		} catch (Exception ex) {
			System.out.println("Could not retrive the user details");
		}
		if (user == null)
			throw new UsernameNotFoundException("User not found with username: " + username);
		return new ParkingBookingUserDetails(user);
	}

}
