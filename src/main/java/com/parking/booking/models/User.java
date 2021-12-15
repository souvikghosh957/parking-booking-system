package com.parking.booking.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
	public static final String TABLE_NAME= "USERS";
	@Id
	@Column(name = "user_id")
	private String userId;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String userRole;
	@Column(name = "user_full_name")
	private String userFullName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "user_location")
	private String userLocation;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public User(String userId, String password, String userFullName, String email, String phone, String userLocation, String role) {
		super();
		this.userId = userId;
		this.password = password;
		this.userFullName = userFullName;
		this.email = email;
		this.phone = phone;
		this.userLocation = userLocation;
		this.userRole = role;
	}

	public User() {

	}

}
