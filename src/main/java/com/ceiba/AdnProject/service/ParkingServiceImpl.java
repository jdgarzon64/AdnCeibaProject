package com.ceiba.AdnProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.repository.IPersistenceRepository;

@Service
public class ParkingServiceImpl implements IParkingService{

	@Autowired
	IPersistenceRepository _IPersistenceRepository;
	@Autowired
	IVehicleFactory _IVehicleFactory;
	
	@Override
	public ResponseDTO<Vehicle> saveVehicle(InputDTO object) {
		Vehicle vehicle = _IVehicleFactory.createVehicle(object);
		Parking parking = new Parking(true,vehicle.getVehicleType().getType(),vehicle);
		_IPersistenceRepository.save(parking);
		return null;
	}

}
