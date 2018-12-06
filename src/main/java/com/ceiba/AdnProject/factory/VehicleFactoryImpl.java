package com.ceiba.AdnProject.factory;

import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.model.Car;
import com.ceiba.AdnProject.model.Motorcycle;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.model.util.VehicleTypeEnum;
@Service
public class VehicleFactoryImpl implements IVehicleFactory{

	@Override
	public Vehicle createVehicle(InputDTO inputDTO) {
		if (inputDTO.getType().equals("Car")) {
			Car car = new Car(inputDTO.getLicence());
			return car;
		} else {
			Motorcycle motorcycle = new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
			return motorcycle;
		}
	}
}
