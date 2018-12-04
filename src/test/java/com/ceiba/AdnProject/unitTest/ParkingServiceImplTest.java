package com.ceiba.AdnProject.unitTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ceiba.AdnProject.dataBuilderTest.ParkingDataBuilderTest;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.repository.IPaymentRepository;
import com.ceiba.AdnProject.repository.IPersistenceRepository;
import com.ceiba.AdnProject.service.ParkingServiceImpl;

import junit.framework.Assert;

public class ParkingServiceImplTest {

	@Mock
	IPersistenceRepository _IPersistenceRepository;
	@Mock
	IPaymentRepository _IPaymentRepository;
	@Mock
	IVehicleFactory _IVehicleFactory;

	ParkingDataBuilderTest dataBuilderTest;
	@InjectMocks
	ParkingServiceImpl parkingServiceImpl;

	@Before
	public void setUp() {
		this.dataBuilderTest = new ParkingDataBuilderTest();

		this.parkingServiceImpl = new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository,
				_IVehicleFactory);
	}

	@Test
	public void saveCarTest() {
		try {
			// Arrange
			when(_IVehicleFactory.createVehicle(null)).thenReturn(dataBuilderTest.createCar());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.save(dataBuilderTest.createParkingCar()));
			// act

			Parking parking = parkingServiceImpl.saveVehicle(null);
			// Assert
			assertEquals(ParkingDataBuilderTest.LICENCE_CAR, parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
