package com.ceiba.adnproject.model;

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
@Table(name = "Vehicle")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehicle", unique = true, nullable = false)
	public int idVehicle;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true,orphanRemoval = true)
	@JoinColumn(name = "id_type")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public VehicleType vehicleType;

	@Column(name = "licence_number", nullable = false)
	public String licenceNumber;

	@Column(name = "engine",nullable =false)
	public String engine;

	public Vehicle() {
		super();
	}

	public int getIdVehicle() {
		return idVehicle;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public String getEngine() {
		return engine;
	}

}
