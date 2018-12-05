package com.ceiba.AdnProject.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.model.util.VehicleTypeEnum;
import com.ceiba.AdnProject.repository.IPaymentRepository;
import com.ceiba.AdnProject.repository.IPersistenceRepository;

@Service
public class ParkingServiceImpl implements IParkingService {
	private static final int CAR_DAY = 8000;
	private static final int CAR_HOUR = 1000;
	private static final int MOTORCYCLE_DAY = 4000;
	private static final int MOTORCYCLE_HOUR = 500;
	private static final int EXTRA_PRICE = 2000;
	private static final int MAX_ENGINE = 500;
	private static final String PATTERN = "^a|^A";
	public static final String VEHICLE_UNKNOW = "This Vehicle doesn't exist";
	public static final String PATTERN_EXCEPTION = "This Vehicle is unauthorized";
	public static final String VEHICLE_REGISTERED_EXCEPTION = "This Vehicle is alredeady resgistered";
	public static final String PARKING_COMPLETE_EXCEPTION = "Sorry but we dont have space for your vehicle";
	private List<Parking> list = new ArrayList<Parking>();

	@Autowired
	IPersistenceRepository _IPersistenceRepository;
	@Autowired
	IPaymentRepository _IPaymentRepository;
	@Autowired
	IVehicleFactory _IVehicleFactory;

	GregorianCalendar calendario = new GregorianCalendar();

	public ParkingServiceImpl() {
		super();
	}

	public ParkingServiceImpl(IPersistenceRepository _IPersistenceRepository, IPaymentRepository _IPaymentRepository,
			IVehicleFactory _IVehicleFactory) {
		this._IPaymentRepository = _IPaymentRepository;
		this._IPersistenceRepository = _IPersistenceRepository;
		this._IVehicleFactory = _IVehicleFactory;
	}

	@Override
	public Parking saveVehicle(InputDTO object) throws ParkingException {
		// getAllVehicles();
		Vehicle vehicle = _IVehicleFactory.createVehicle(object);
		Parking p = findVehicle(vehicle.getLicenceNumber().toUpperCase());
		if (p != null)
			throw new ParkingException(VEHICLE_REGISTERED_EXCEPTION);
		if (!completeVehicle(vehicle.getVehicleType().getType().toUpperCase()))
			throw new ParkingException(PARKING_COMPLETE_EXCEPTION);
		verifyLicence(vehicle.getLicenceNumber());
		Parking parking = new Parking(true, vehicle.getVehicleType().getType(), vehicle);
		_IPersistenceRepository.save(parking);
		return parking;
	}

	@Override
	public List<Parking> getAllParkings() {
		return (List<Parking>) _IPersistenceRepository.findAll();
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

		calendario.setTime(new Date());
		if (calendario.get(Calendar.DAY_OF_WEEK) == calendario.SUNDAY
				|| calendario.get(Calendar.DAY_OF_WEEK) == calendario.MONDAY) {
			return false;
		}
		return true;
	}

	public boolean verifyEngine(String engine) {
		if (Integer.parseInt(engine) > MAX_ENGINE)
			return true;
		return false;
	}

	@Override
	public Payment generatePayment(InputDTO object) throws ParkingException {
		return generatePayment(object.getLicence());
	}
	
	public void getAllVehicles() {
		this.list = (List<Parking>) _IPersistenceRepository.findAll();
	}

	public Parking findVehicle(String licence) {
		getAllVehicles();
		if (this.list != null) {
			for (Parking parking : this.list) {
				if (parking.getVehicle().getLicenceNumber().toUpperCase().equals(licence))
					return parking;
			}
		}
		return null;
	}

	public Payment generatePayment(String licence) throws ParkingException {
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(new Date());
		Parking parking = findVehicle(licence.toUpperCase());
		if (parking == null)
			throw new ParkingException(VEHICLE_UNKNOW);
		int timeInside = timeIside(parking.getDateIn(), calendario.getTime());
		double totalPrice = 0;
		int priceByHour = 0;
		if (parking.getVehicle().getVehicleType().getType().equals(VehicleTypeEnum.CAR.name())) {
			priceByHour = CAR_HOUR;
			totalPrice = generatePrice(timeInside, 0, CAR_HOUR, CAR_DAY);
		} else if (parking.getVehicle().getVehicleType().getType().toUpperCase()
				.equals(VehicleTypeEnum.MOTORCYCLE.name())) {
			priceByHour = MOTORCYCLE_HOUR;
			totalPrice = generatePrice(timeInside, 0, MOTORCYCLE_HOUR, MOTORCYCLE_DAY);
		}
		if (Integer.parseInt(parking.getVehicle().getEngine()) > MAX_ENGINE) {
			totalPrice += EXTRA_PRICE;
		}
		Payment payment = new Payment(parking.getVehicle(), parking.getDateIn().toString(),
				calendario.getTime().toString(), timeInside, String.valueOf(priceByHour), String.valueOf(totalPrice));
		System.out.println("fecha entrada " + parking.getDateIn().toString());
		System.out.println("fecha salida " + calendario.getTime().toString());
		System.out.println("tiempo adentro " + timeInside);
		System.out.println("precio total" + totalPrice);
		_IPaymentRepository.save(payment);
		quitVehicle(licence.toUpperCase());
		return payment;
	}

	public int timeIside(Date in, Date out) {

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
		long diff = out.getTime() - in.getTime();

		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("difference between days: " + diffDays);

		int diffhours = (int) (diff / (60 * 60 * 1000));
		System.out.println("difference between hours: " + crunchifyFormatter.format(diffhours));

		return diffhours;
	}

	public double generatePrice(int hours, double price, int vehicleHour, int VehicleDay) {
		if (hours == 0) {
			price += vehicleHour;
		}
		if (hours > 0 && hours < 9) {
			price += (hours + 1) * vehicleHour;
		}
		if (hours >= 9 && hours < 24) {
			price += VehicleDay;
		}
		if (hours >= 24) {
			price += VehicleDay;
			return generatePrice(hours - 24, price, vehicleHour, VehicleDay);
		}
		return price;
	}

	public boolean quitVehicle(String licence) {
		Parking parking = findVehicle(licence.toUpperCase());
		_IPersistenceRepository.delete(parking);
		return true;
	}

	public boolean completeVehicle(String type) {
		getAllVehicles();
		int count = 0;
		if (this.list != null) {
			for (Parking parking : list) {
				if (parking.getType().toUpperCase().equals(type)) {
					count++;
				}
			}
			if (type.equals(VehicleTypeEnum.MOTORCYCLE.name()) && count >= 10)
				return false;
			if (type.equals(VehicleTypeEnum.CAR.name()) && count >= 20)
				return false;
		}
		return true;
	}

	public void setCalendario(GregorianCalendar calendario) {
		this.calendario = calendario;
	}

}
