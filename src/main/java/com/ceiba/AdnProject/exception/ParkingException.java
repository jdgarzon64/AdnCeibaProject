package com.ceiba.AdnProject.exception;

public class ParkingException extends Exception {

	private String message;
	public ParkingException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
