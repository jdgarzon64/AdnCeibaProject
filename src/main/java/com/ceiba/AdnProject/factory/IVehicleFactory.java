package com.ceiba.AdnProject.factory;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.model.Vehicle;

public interface IVehicleFactory {

	public Vehicle createVehicle(InputDTO inputDTO);
	
}
