package com.ceiba.AdnProject.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public static final int MAX_ENGINE = 500;
	private static final String PATTERN = "^a|^A";
	public static final String VEHICLE_UNKNOW = "This Vehicle doesn't exist";
	public static final String PATTERN_EXCEPTION = "This Vehicle is unauthorized";
	public static final String VEHICLE_REGISTERED_EXCEPTION = "This Vehicle is alredeady resgistered";
	public static final String PARKING_COMPLETE_EXCEPTION = "Sorry but we dont have space for your vehicle";
	private List<Parking> list = new ArrayList<Parking>();

	@Autowired
	private IPersistenceRepository iPersistenceRepository;
	@Autowired
	private IPaymentRepository iPaymentRepository;
	@Autowired
	private IVehicleFactory iVehicleFactory;

	GregorianCalendar calendario = new GregorianCalendar();
	public ParkingServiceImpl() {
		super();
	}

	public ParkingServiceImpl(IPersistenceRepository iPersistenceRepository, IPaymentRepository iPaymentRepository,
			IVehicleFactory iVehicleFactory) {
		this.iPaymentRepository = iPaymentRepository;
		this.iPersistenceRepository = iPersistenceRepository;
		this.iVehicleFactory = iVehicleFactory;
	}

	@Override
	public Parking saveVehicle(InputDTO object) throws ParkingException {
		Vehicle vehicle = iVehicleFactory.createVehicle(object);
		Parking p = findVehicle(vehicle.getLicenceNumber().toUpperCase());
		if (p != null)
			throw new ParkingException(VEHICLE_REGISTERED_EXCEPTION);
		if (isCompleteVehicle(vehicle.getVehicleType().getType().toUpperCase()))
			throw new ParkingException(PARKING_COMPLETE_EXCEPTION);
		verifyLicence(vehicle.getLicenceNumber());
		Parking parking = new Parking(true, vehicle.getVehicleType().getType(), vehicle);
		iPersistenceRepository.save(parking);
		return parking;
	}

	@Override
	public List<Parking> getAllParkings() {
		return iPersistenceRepository.findAll();
	}

	public boolean verifyLicence(String licence) throws ParkingException {
		Pattern p = Pattern.compile(PATTERN);
		Matcher m = p.matcher(licence);
		if (m.find() && verifyDay()) {
			throw new ParkingException(PATTERN_EXCEPTION);
		}
		return true;
	}

	public boolean verifyDay() {
		calendario.setTime(new Date());
		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
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
		this.list = iPersistenceRepository.findAll();
	}

	public Parking findVehicle(String licence) {
		getAllVehicles();
		if (this.list != null) {
			for (Parking parking : this.list) {
				if (parking.getVehicle().getLicenceNumber().equalsIgnoreCase(licence))
					return parking;
			}
		}
		return null;

	}

	public Payment generatePayment(String licence) throws ParkingException {
		calendario.setTime(new Date());
		Parking parking = findVehicle(licence.toUpperCase());
		if (parking == null)
			throw new ParkingException(VEHICLE_UNKNOW);
		int timeInside = timeIside(parking.getDateIn(), calendario.getTime());
		double totalPrice = 0;
		int priceByHour = 0;
		if (parking.getVehicle().getVehicleType().getType().equalsIgnoreCase(VehicleTypeEnum.CAR.name())) {
			priceByHour = CAR_HOUR;
			totalPrice = generatePrice(timeInside, 0, CAR_HOUR, CAR_DAY);
		} else if (parking.getVehicle().getVehicleType().getType()
				.equalsIgnoreCase(VehicleTypeEnum.MOTORCYCLE.name())) {
			priceByHour = MOTORCYCLE_HOUR;
			totalPrice = generatePrice(timeInside, 0, MOTORCYCLE_HOUR, MOTORCYCLE_DAY);
		}
		if (Integer.parseInt(parking.getVehicle().getEngine()) > MAX_ENGINE) {
			totalPrice += EXTRA_PRICE;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Payment payment = new Payment(parking.getVehicle(), parking.getDateIn().toString(),
				String.valueOf(dateFormat.format(date)), timeInside, String.valueOf(priceByHour), String.valueOf(totalPrice));
		iPaymentRepository.save(payment);
		quitVehicle(licence.toUpperCase());
		return payment;
	}

	public int timeIside(Date in, Date out) {
		long diff = out.getTime() - in.getTime();
		int diffhours = (int) (diff / (60 * 60 * 1000));
		return diffhours;
	}

	public double generatePrice(int hours, double price, int vehicleHour, int vehicleDay) {
		if (hours == 0) {
			price += vehicleHour;
		}
		if (hours > 0 && hours < 9) {
			price += (hours + 1) * vehicleHour;
		}
		if (hours >= 9 && hours < 24) {
			price += vehicleDay;
		}
		if (hours >= 24) {
			price += vehicleDay;
			return generatePrice(hours - 24, price, vehicleHour, vehicleDay);
		}
		return price;
	}

	public boolean quitVehicle(String licence) {
		Parking parking = findVehicle(licence.toUpperCase());
		iPersistenceRepository.delete(parking);
		return true;
	}

	public int countVehicleByType(String vehicleType) {
		int count = 0;
		if (this.list != null) {
			for (Parking parking : list) {
				if (parking.getVehicle().getVehicleType().getType().equals(vehicleType)) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean isCompleteVehicle(String vehicleType) {
		int countVehicles = countVehicleByType(vehicleType);
		if (vehicleType.equals(VehicleTypeEnum.CAR.name()) && countVehicles < Parking.MAX_CARS)
			return false;
		if (vehicleType.equals(VehicleTypeEnum.MOTORCYCLE.name()) && countVehicles < Parking.MAX_MOTORCYCLES)
			return false;
		return true;
	}

	public void setCalendario(GregorianCalendar calendario) {
		this.calendario = calendario;
	}

}
