package com.ceiba.AdnProject.service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.model.Vehicle;

public interface IParkingService {

	public ResponseDTO<Vehicle> saveVehicle(InputDTO object);
}
