package com.ceiba.adnproject.dataBuilderTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ceiba.adnproject.model.Car;
import com.ceiba.adnproject.model.Motorcycle;
import com.ceiba.adnproject.model.Parking;
import com.ceiba.adnproject.model.Vehicle;
import com.ceiba.adnproject.model.util.VehicleTypeEnum;

public class ParkingDataBuilder {

	public static final String LICENCE_CAR = "EBC123";
	public static final String LICENCE_MOTORCYCLE = "QWE123";
	public static final String LICENCE_MOTORCYCLE_PLUS = "ZXC123";
	public static final String ENGINE_MOTORCYCLE = "400";
	public static final String ENGINE_MOTORCYCLE_PLUS = "600";
	public static final String DATE = "2018-12-04T5:08:56.235-07:00 ";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	Date dateMock;
	
	public Vehicle createMotorCycle() {
		return new Motorcycle(LICENCE_MOTORCYCLE, ENGINE_MOTORCYCLE);
	}

	public Vehicle createMotorCyclePlusEngine() {
		return new Motorcycle(LICENCE_MOTORCYCLE_PLUS, ENGINE_MOTORCYCLE_PLUS);
	}

	public Vehicle createCar() {
		return new Car(LICENCE_CAR, ENGINE_MOTORCYCLE);
	}

	public Vehicle createMotorcycle() {
		return new Motorcycle(LICENCE_MOTORCYCLE, ENGINE_MOTORCYCLE);
	}

	public Vehicle createMotorcyclePlus() {
		return new Motorcycle(LICENCE_MOTORCYCLE_PLUS, ENGINE_MOTORCYCLE_PLUS);
	}

	public Parking createParkingCar() {
		Parking parking = new Parking(true, VehicleTypeEnum.CAR.name(), createCar());
		try {
			dateMock = formatter.parse(DATE);
			parking.setDateIn(dateMock);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return parking;
	}

	public Parking createParkingMotorcyclePlus() {
		Parking parking = new Parking(true, VehicleTypeEnum.MOTORCYCLE.name(), createMotorcyclePlus());
		try {
			dateMock = formatter.parse(DATE);
			parking.setDateIn(dateMock);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return parking;
	}

	public Parking createParkingMotorcycle() {
		Parking parking = new Parking(true, VehicleTypeEnum.CAR.name(), createMotorcycle());
		try {
			dateMock = formatter.parse(DATE);
			parking.setDateIn(dateMock);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parking;
	}
}
