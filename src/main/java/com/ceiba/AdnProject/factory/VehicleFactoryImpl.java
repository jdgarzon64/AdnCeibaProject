package com.ceiba.AdnProject.factory;

import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.model.Car;
import com.ceiba.AdnProject.model.Motorcycle;
import com.ceiba.AdnProject.model.Vehicle;
@Service
public class VehicleFactoryImpl implements IVehicleFactory{

	@Override
	public Vehicle createVehicle(InputDTO inputDTO) {
		if (inputDTO.getType().toUpperCase().equals("CAR")) {
			return new Car(inputDTO.getLicence());
		} else {
			Motorcycle motorcycle = new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
			return motorcycle;
		}
	}
}
