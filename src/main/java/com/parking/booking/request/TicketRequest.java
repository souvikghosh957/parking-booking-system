package com.parking.booking.request;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketRequest {
	private String parkingArea;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookFrom;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookTo;
	@JsonProperty
	private boolean isDailyRecurring;
	private int numberOfDays;
	@JsonProperty
	private boolean isWeeklyRecurring;
	private int numberOfWeeks;
	@JsonProperty
	private boolean isMonthlyRecurring;
	private int numberOfMonths;

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getNumberOfWeeks() {
		return numberOfWeeks;
	}

	public void setNumberOfWeeks(int numberOfWeeks) {
		this.numberOfWeeks = numberOfWeeks;
	}

	public int getNumberOfMonths() {
		return numberOfMonths;
	}

	public void setNumberOfMonths(int numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	public String getParkingArea() {
		return parkingArea;
	}

	public void setParkingArea(String parkingArea) {
		this.parkingArea = parkingArea;
	}

	public Date getBookFrom() {
		return bookFrom;
	}

	public void setBookFrom(Date bookFrom) {
		this.bookFrom = bookFrom;
	}

	public Date getBookTo() {
		return bookTo;
	}

	public void setBookTo(Date bookTo) {
		this.bookTo = bookTo;
	}

	public boolean isWeeklyRecurring() {
		return isWeeklyRecurring;
	}

	public void setWeeklyRecurring(boolean isWeeklyRecurring) {
		this.isWeeklyRecurring = isWeeklyRecurring;
	}

	public boolean isDailyRecurring() {
		return isDailyRecurring;
	}

	public void setDailyRecurring(boolean isDailyRecurring) {
		this.isDailyRecurring = isDailyRecurring;
	}

	public boolean isMonthlyRecurring() {
		return isMonthlyRecurring;
	}

	public void setMonthlyRecurring(boolean isMonthlyRecurring) {
		this.isMonthlyRecurring = isMonthlyRecurring;
	}

	public TicketRequest(String parkingArea, Date bookFrom, Date bookTo, boolean isDailyRecurring, int numberOfDays,
			boolean isWeeklyRecurring, int numberOfWeeks, boolean isMonthlyRecurring, int numberOfMonths) {
		super();
		this.parkingArea = parkingArea;
		this.bookFrom = bookFrom;
		this.bookTo = bookTo;
		this.isDailyRecurring = isDailyRecurring;
		this.numberOfDays = numberOfDays;
		this.isWeeklyRecurring = isWeeklyRecurring;
		this.numberOfWeeks = numberOfWeeks;
		this.isMonthlyRecurring = isMonthlyRecurring;
		this.numberOfMonths = numberOfMonths;
	}

	public TicketRequest() {

	}

}
