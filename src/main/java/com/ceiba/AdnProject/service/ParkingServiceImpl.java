package com.ceiba.AdnProject.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.repository.IPersistenceRepository;

@Service
public class ParkingServiceImpl implements IParkingService {

	private static final String PATTERN = "^a|^A";
	private static final String PATTERN_EXCEPTION = "This Vehicle is unauthorized";

	@Autowired
	IPersistenceRepository _IPersistenceRepository;
	@Autowired
	IVehicleFactory _IVehicleFactory;

	@Override
	public Parking saveVehicle(InputDTO object) throws ParkingException {
		Vehicle vehicle = _IVehicleFactory.createVehicle(object);
		verifyLicence(vehicle.getLicenceNumber());
		Parking parking = new Parking(true, vehicle.getVehicleType().getType(), vehicle);
		
		_IPersistenceRepository.save(parking);
		return parking;
	}

	public boolean verifyLicence(String licence) throws ParkingException {
		Pattern p = Pattern.compile(PATTERN);
		Matcher m = p.matcher(licence);
		if (m.find() && verifyDay() == true) {
			throw new ParkingException(PATTERN_EXCEPTION);
		}
		return true;
	}

	public boolean verifyDay() {
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(new Date());
		if (calendario.get(Calendar.DAY_OF_WEEK) == calendario.SUNDAY
				|| calendario.get(Calendar.DAY_OF_WEEK) == calendario.MONDAY) {
			return true;
		}

		return false;
	}
}
