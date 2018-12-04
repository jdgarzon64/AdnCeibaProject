package com.ceiba.AdnProject.dataBuilderTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ceiba.AdnProject.model.Car;
import com.ceiba.AdnProject.model.Motorcycle;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.model.util.VehicleTypeEnum;

public class ParkingDataBuilderTest {

	public static final String LICENCE_CAR = "ABC123";
	private static final String LICENCE_MOTORCYCLE = "QWE123";
	private static final String LICENCE_MOTORCYCLE_PLUS = "ZXC123";
	private static final String ENGINE_MOTORCYCLE = "400";
	private static final String ENGINE_MOTORCYCLE_PLUS = "600";

	public Vehicle createMotorCycle() {
		return new Motorcycle(LICENCE_MOTORCYCLE, ENGINE_MOTORCYCLE);
	}

	public Vehicle createMotorCyclePlusEngine() {
		return new Motorcycle(LICENCE_MOTORCYCLE_PLUS, ENGINE_MOTORCYCLE_PLUS);
	}

	public Vehicle createCar() {
		return new Car(LICENCE_CAR);
	}

	public Parking createParkingCar() {
		// boolean status, String type, Vehicle vehicle
		Parking parking = new Parking(true, VehicleTypeEnum.CAR.name(), createCar());
		String date = "Thu, Dic 03 2018 23:37:50";
		SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
		Date dateMock;
		try {
			dateMock = formatter.parse(date);
			parking.setDateIn(dateMock);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parking;
	}
}
