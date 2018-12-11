package com.ceiba.adnproject.factory;

import org.springframework.stereotype.Service;

import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.exception.ParkingException;
import com.ceiba.adnproject.model.Car;
import com.ceiba.adnproject.model.Motorcycle;
import com.ceiba.adnproject.model.Vehicle;
@Service
public class VehicleFactoryImpl implements IVehicleFactory{
public static final String LICENCE_INVALID=	"Licence Invalid";
	@Override
	public Vehicle createVehicle(InputDTO inputDTO) throws ParkingException{
		if(!verifyLicence(inputDTO.getEngine()))  throw new ParkingException (LICENCE_INVALID);
		if (inputDTO.getType().equalsIgnoreCase("CAR")) {
			return new Car(inputDTO.getLicence(),inputDTO.getEngine());
		} else {
			return new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
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
