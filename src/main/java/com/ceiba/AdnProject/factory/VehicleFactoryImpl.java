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

		if (inputDTO.getEngine().equals("0")) {
			Car car = new Car(inputDTO.getLicence());
			System.out.println("i am a car :D");
			return car;
		} else {
			Motorcycle motorcycle = new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
			System.out.println("i am a motorcycle :D");
			return motorcycle;
		}
	}
}
