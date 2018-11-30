package com.ceiba.AdnProject.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.model.util.VehicleTypeEnum;
import com.ceiba.AdnProject.repository.IPersistenceRepository;

@Service
public class ParkingServiceImpl implements IParkingService {
	private static final int CAR_DAY = 8000;
	private static final int CAR_HOUR = 1000;
	private static final int MOTORCYCLE_DAY = 4000;
	private static final int MOTORCYCLE_HOUR = 500;
	private static final int EXTRA_PRICE = 650;
	private static final int MAX_ENGINE = 500;
	private static final String PATTERN = "^a|^A";
	private static final String VEHICLE_UNKNOW = "This Vehicle doesn't exist";
	private static final String PATTERN_EXCEPTION = "This Vehicle is unauthorized";
	private static final String VEHICLE_REGISTERED_EXCEPTION = "This Vehicle is alredeady resgistered";
	private List<Parking> list;

	@Autowired
	IPersistenceRepository _IPersistenceRepository;
	@Autowired
	IVehicleFactory _IVehicleFactory;

	public ParkingServiceImpl() {
		super();
		// getAllVehicles();
	}

	@Override
	public Parking saveVehicle(InputDTO object) throws ParkingException {
		Vehicle vehicle = _IVehicleFactory.createVehicle(object);
		Parking p = findVehicle(vehicle.getLicenceNumber());
		if (p != null)
			throw new ParkingException(VEHICLE_REGISTERED_EXCEPTION);
		verifyLicence(vehicle.getLicenceNumber());
		Parking parking = new Parking(true, vehicle.getVehicleType().getType(), vehicle);
// TODO DTO response
		_IPersistenceRepository.save(parking);
		getAllVehicles();
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

	public boolean verifyEngine(String engine) {
		if (Integer.parseInt(engine) > MAX_ENGINE)
			return true;
		return false;
	}

	@Override
	public Parking generatePayment(InputDTO object) throws ParkingException {
		generatePayment(object.getLicence());
		return null;
	}

	@PostConstruct
	public void getAllVehicles() {
		this.list = (List<Parking>) _IPersistenceRepository.findAll();
	}

	public Parking findVehicle(String licence) {
		for (Parking parking : this.list) {
			if (parking.getVehicle().getLicenceNumber().equals(licence))
				return parking;
		}
		return null;
	}

	public double generatePayment(String licence) throws ParkingException {
		Parking parking = findVehicle(licence);
		if(parking == null) throw new ParkingException(VEHICLE_UNKNOW);
		Date checkOut = new Date ();
		Date timeInside = timeIside(parking.getDateIn());
		int priceByHour =0;
		if(parking.getVehicle().getVehicleType().equals(VehicleTypeEnum.CAR)) {
			priceByHour = CAR_HOUR;
		}else if (parking.getVehicle().getVehicleType().equals(VehicleTypeEnum.MOTORCYCLE)) {
			priceByHour = MOTORCYCLE_HOUR;
		}
		Payment payment = new Payment(parking.getVehicle(),parking.getDateIn().toString(),
				checkOut.toString(),timeInside.toString(),String.valueOf(priceByHour),String.valueOf(priceByHour));
		System.out.println("fecha entrada "+ parking.getDateIn().toString());
		System.out.println("fecha salida "+ checkOut.toString());
		System.out.println("tiempo adentro "+ timeInside.toString());
		return 0;
	}

	public Date timeIside(Date in) {
		Date out = new Date();
		Date response = new Date(out.getTime() - in.getTime());
		return response;
	}
}
