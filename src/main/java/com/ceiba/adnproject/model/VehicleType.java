package com.ceiba.adnproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleType")
public class VehicleType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_type", unique = true, nullable = false)
	private int idType;
	
	@Column(name = "type", nullable = false)
	private String type;

	public VehicleType() {
		super();
	}

	public VehicleType(String type) {
		super();
		this.type = type;
	}

	public int getIdType() {
		return idType;
	}

	public String getType() {
		return type;
	}
}
