package com.ceiba.AdnProject.model;

import com.ceiba.AdnProject.model.util.VehicleTypeEnum;

public class Car extends Vehicle {
	private final int CAPACIDAD_MAXIMA = 20;

	public Car() {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
	}

	public Car(String licence) {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
		this.licenceNumber = licence;
		this.engine = "0";
	}

	public int obtenerCApacidadMAxima() {
		return CAPACIDAD_MAXIMA;
	}
}
