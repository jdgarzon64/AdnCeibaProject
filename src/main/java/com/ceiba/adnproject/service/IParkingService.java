package com.ceiba.adnproject.service;

import java.util.List;

import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.exception.ParkingException;
import com.ceiba.adnproject.model.Parking;
import com.ceiba.adnproject.model.Payment;

public interface IParkingService {

	public Parking saveVehicle(InputDTO object) throws ParkingException;
	
	public Payment generatePayment(InputDTO object) throws ParkingException;
	
	public List<Parking> getAllParkings();
	
}
