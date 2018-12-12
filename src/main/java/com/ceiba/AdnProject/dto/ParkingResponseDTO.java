package com.ceiba.adnproject.dto;

import java.io.Serializable;

import com.ceiba.adnproject.model.Parking;

public class ParkingResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String message;
	public Parking parking;
	public ParkingResponseDTO(String message, Parking parking) {
		super();
		this.message = message;
		this.parking = parking;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Parking getParking() {
		return parking;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	
	
}
