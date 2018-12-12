package com.ceiba.adnproject.factory;

import org.springframework.stereotype.Service;
import com.ceiba.adnproject.constants.InvalidMessageResponse;
import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.exception.ParkingException;
import com.ceiba.adnproject.model.Car;
import com.ceiba.adnproject.model.Motorcycle;
import com.ceiba.adnproject.model.Vehicle;
import com.ceiba.adnproject.model.util.VehicleTypeEnum;

@Service
public class VehicleFactoryImpl implements IVehicleFactory{

	@Override
	public Vehicle createVehicle(InputDTO inputDTO) throws ParkingException{
		if(!verifyEngine(inputDTO.getEngine()))  throw new ParkingException (InvalidMessageResponse.ENGINE_INVALID);
		if (inputDTO.getType().equalsIgnoreCase(VehicleTypeEnum.CAR.name())) {
			return new Car(inputDTO.getLicence(),inputDTO.getEngine());
		} else {
			return new Motorcycle(inputDTO.getLicence(), inputDTO.getEngine());
		}
	}
	
	public boolean verifyEngine (String value) throws ParkingException {
		try {	
		Integer.parseInt(value);
			return true;
		}catch(Exception e) {
			 throw new ParkingException (InvalidMessageResponse.ENGINE_INVALID);
		}
	}
}
