package com.ceiba.adnproject.model;

import com.ceiba.adnproject.model.util.VehicleTypeEnum;

public class Car extends Vehicle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Car(String licence,String engine) {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.CAR.name());
		this.licenceNumber = licence;
		this.engine = engine;
	}

}
