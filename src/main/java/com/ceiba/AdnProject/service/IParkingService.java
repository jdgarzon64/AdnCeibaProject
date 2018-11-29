package com.ceiba.AdnProject.service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Vehicle;

public interface IParkingService {

	public Parking saveVehicle(InputDTO object) throws ParkingException;
}
