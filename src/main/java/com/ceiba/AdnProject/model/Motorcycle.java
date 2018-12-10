package com.ceiba.AdnProject.model;

import com.ceiba.AdnProject.model.util.VehicleTypeEnum;

public class Motorcycle extends Vehicle  {

	public Motorcycle() {
		super();
		this.vehicleType = new VehicleType(VehicleTypeEnum.MOTORCYCLE.name());
	}
	
	public Motorcycle(String licence,String engine) {
		super();
		this.licenceNumber= licence;
		this.engine = engine;
		this.vehicleType = new VehicleType(VehicleTypeEnum.MOTORCYCLE.name());
	}
	
}
