package com.ceiba.adnproject.dto;

import java.io.Serializable;

import com.ceiba.adnproject.model.Payment;

public class PaymentResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String message;
	public Payment payment;
	public PaymentResponseDTO(String message, Payment payment) {
		super();
		this.message = message;
		this.payment = payment;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
