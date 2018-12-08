package com.ceiba.AdnProject.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Payment")
public class Payment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPayment", unique = true, nullable = false)
	private int idPayment;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true,orphanRemoval = true)
	//@ManyToOne
	@JoinColumn(name = "id_vehicle",nullable =false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Vehicle vehicle;
	
	@Column(name = "hourCheckIn", nullable = false)
	private String hourCheckIn;
	
	@Column(name = "hourCheckOut", nullable = false)
	private String hourCheckOut;
	
	@Column(name = "timeInside", nullable = false)
	private int timeInside;
	
	@Column(name = "priceByHour", nullable = false)
	private String priceByHour;
	
	@Column(name = "totalPrice", nullable = false)
	private String totalPrice;

	public Payment() {
		super();
	}

	public Payment(Vehicle vehicle, String hourCheckIn, String hourCheckOut, int timeInside, String priceByHour,
			String totalPrice) {
		super();
		this.vehicle = vehicle;
		this.hourCheckIn = hourCheckIn;
		this.hourCheckOut = hourCheckOut;
		this.timeInside = timeInside;
		this.priceByHour = priceByHour;
		this.totalPrice = totalPrice;
	}

	public int getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getHourCheckIn() {
		return hourCheckIn;
	}

	public void setHourCheckIn(String hourCheckIn) {
		this.hourCheckIn = hourCheckIn;
	}

	public String getHourCheckOut() {
		return hourCheckOut;
	}

	public void setHourCheckOut(String hourCheckOut) {
		this.hourCheckOut = hourCheckOut;
	}

	public int getTimeInside() {
		return timeInside;
	}

	public void setTimeInside(int timeInside) {
		this.timeInside = timeInside;
	}

	public String getPriceByHour() {
		return priceByHour;
	}

	public void setPriceByHour(String priceByHour) {
		this.priceByHour = priceByHour;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	

	
}
