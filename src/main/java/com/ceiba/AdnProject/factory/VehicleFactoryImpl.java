package com.ceiba.AdnProject.factory;

import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.model.Car;
import com.ceiba.AdnProject.model.Motorcycle;
import com.ceiba.AdnProject.model.Vehicle;
@Service
public class VehicleFactoryImpl implements IVehicleFactory{
public static String LICENCE_INVALID=	"Licence Invalid";
	@Override
	public Vehicle createVehicle(InputDTO inputDTO) throws ParkingException{
		if(!verifyLicence(inputDTO.getEngine()))  throw new ParkingException (LICENCE_INVALID);
		if (inputDTO.getType().equalsIgnoreCase("CAR")) {
			return new Car(inputDTO.getLicence());
		} else {
			Motorcycle motorcycle = new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
			return motorcycle;
		}
	}
	
	public boolean verifyLicence (String value) throws ParkingException {
		try {	
		Integer.parseInt(value);
			return true;
		}catch(Exception e) {
			 throw new ParkingException (LICENCE_INVALID);
		}
	}
}
