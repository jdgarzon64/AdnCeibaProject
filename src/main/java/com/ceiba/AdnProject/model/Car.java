package com.ceiba.AdnProject.model;

import com.ceiba.AdnProject.model.util.VehicleTypeEnum;

public class Car extends Vehicle {

	public Car() {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
	}

	public Car(String licence) {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
		this.licenceNumber = licence;
	}
}