package com.ceiba.adnproject.factory;

import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.exception.ParkingException;
import com.ceiba.adnproject.model.Vehicle;

public interface IVehicleFactory {

	public Vehicle createVehicle(InputDTO inputDTO) throws ParkingException;
	
}
