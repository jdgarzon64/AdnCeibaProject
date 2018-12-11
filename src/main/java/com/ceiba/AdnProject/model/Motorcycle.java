package com.ceiba.adnproject.model;
import com.ceiba.adnproject.model.util.VehicleTypeEnum;

public class Motorcycle extends Vehicle  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Motorcycle(String licence,String engine) {
		super();
		this.licenceNumber= licence;
		this.engine = engine;
		this.vehicleType = new VehicleType(VehicleTypeEnum.MOTORCYCLE.name());
	}
	
}
