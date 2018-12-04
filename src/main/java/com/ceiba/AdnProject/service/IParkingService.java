package com.ceiba.AdnProject.service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;

public interface IParkingService {

	public Parking saveVehicle(InputDTO object) throws ParkingException;
	
	public Payment generatePayment(InputDTO object) throws ParkingException;
	
}
