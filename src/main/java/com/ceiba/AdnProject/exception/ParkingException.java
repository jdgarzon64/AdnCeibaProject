package com.ceiba.AdnProject.exception;

import org.springframework.http.HttpStatus;

public class ParkingException extends Exception {

	private String message;
	private HttpStatus httpStatus;

	public ParkingException(String message) {
		super();
		this.message = message;
		//this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
