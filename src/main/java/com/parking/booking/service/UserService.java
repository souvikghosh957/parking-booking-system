package com.parking.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.booking.models.User;
import com.parking.booking.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public String registerUser(User user) {
		if (!checkIfUserExists(user)) {
			return userRepository.save(user).getUserId();
		} else {
			return null;
		}
	}

	private boolean checkIfUserExists(User user) {
		return userRepository.getByUserId(user.getUserId()) != null;
	}

}
