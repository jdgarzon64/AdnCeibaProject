package com.ceiba.AdnProject.model;

import com.ceiba.AdnProject.model.util.VehicleTypeEnum;

public class Car extends Vehicle {

	public Car(String licence,String engine) {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
		this.licenceNumber = licence;
		this.engine = engine;
	}

}
