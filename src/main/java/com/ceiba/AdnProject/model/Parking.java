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

@Entity
@Table(name = "Parking")
public class Parking implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idParking", unique = true, nullable = false)
	private int idParking;
	
	@Column(name = "status", nullable = false)
	private boolean status;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
			optional = true)
	private Vehicle vehicle;
	
	public Parking() {
		super();
	}

	public Parking(boolean status, String type, Vehicle vehicle) {
		super();
		this.status = status;
		this.type = type;
		this.vehicle = vehicle;
	}

	public int getIdParking() {
		return idParking;
	}

	public void setIdParking(int idParking) {
		this.idParking = idParking;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
}
