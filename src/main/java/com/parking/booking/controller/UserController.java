package com.parking.booking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.booking.models.User;
import com.parking.booking.response.ResponseMessage;
import com.parking.booking.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userservice;

	@PostMapping(value = "/registerUser", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseMessage> registerUser(@RequestBody User user) {
		String message = "";
		if (user != null) {
			try {
				String userId = userservice.registerUser(user);
				if (userId != null && !userId.isBlank()) {
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(new ResponseMessage("User created success fully"));
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ResponseMessage("User already exist. Enter another user name."));
				}
			} catch (Exception ex) {
				logger.error("Exception occured while adding locations: " + ex.getMessage());
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.body(new ResponseMessage("Error occured. Could not create the User."));
			}
		}
		message = "Please provide non empty requests!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

}
