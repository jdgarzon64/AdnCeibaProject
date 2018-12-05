package com.ceiba.AdnProject.unitTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.ceiba.AdnProject.dataBuilderTest.ParkingDataBuilderTest;
import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.repository.IPaymentRepository;
import com.ceiba.AdnProject.repository.IPersistenceRepository;
import com.ceiba.AdnProject.service.ParkingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceTest {

	@Mock
	IPersistenceRepository _IPersistenceRepository;
	@Mock
	IPaymentRepository _IPaymentRepository;
	@Mock
	IVehicleFactory _IVehicleFactory;
	@InjectMocks
	ParkingServiceImpl parkingServiceImpl;

	ParkingDataBuilderTest dataBuilderTest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		// _IPersistenceRepository = mock(IPersistenceRepository.class);
		// _IPaymentRepository = mock(IPaymentRepository.class);
		// _IVehicleFactory = mock(IVehicleFactory.class);
		parkingServiceImpl = mock(ParkingServiceImpl.class);
		dataBuilderTest = mock(ParkingDataBuilderTest.class);
		parkingServiceImpl = spy(new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository,
				_IVehicleFactory));
		this.dataBuilderTest = new ParkingDataBuilderTest();
		//this.parkingServiceImpl = new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository,
				//_IVehicleFactory);
	}

	@Test
	public void saveCarTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			// when(_IPersistenceRepository.save(dataBuilderTest.createParkingCar()))
			// .thenReturn(dataBuilderTest.createParkingCar());
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// when(parkingServiceImpl.completeVehicle("")).thenReturn(true);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilderTest.LICENCE_CAR, parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void vehicleRegisteredExceptionTest() throws ParkingException {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			List<Parking> list = new ArrayList<Parking>();
			List<Parking> spyList = Mockito.spy(list);
			spyList.add(dataBuilderTest.createParkingCar());
		
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(_IPersistenceRepository.findAll()).thenReturn(spyList);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert
			//System.out.println(e.getMessage());
			assertEquals(ParkingServiceImpl.VEHICLE_REGISTERED_EXCEPTION, e.getMessage());
		}
	}
	

	@Test
	public void parkingCompleteExceptionTest() throws ParkingException {
		try {
			// Arrange
		
			InputDTO dto = new InputDTO("", "");
			List<Parking> list = new ArrayList<Parking>();
			List<Parking> spyList = Mockito.spy(list);
			//spyList.add(dataBuilderTest.createParkingCar());
			
			/*
			IVehicleFactory _IVehicleFactory = mock(IVehicleFactory.class);
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			
			IPersistenceRepository _IPersistenceRepository = mock(IPersistenceRepository.class);
			when(_IPersistenceRepository.findAll()).thenReturn(list);
			*/
			
			ParkingServiceImpl parkingServiceImpl = spy(new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository,
					_IVehicleFactory));

			Mockito.doReturn(dataBuilderTest.createCar()).when(_IVehicleFactory).createVehicle(dto);
			//when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			Mockito.lenient().doReturn(null).when(_IPersistenceRepository).findAll();
			Mockito.doReturn(false).when(parkingServiceImpl).completeVehicle(Mockito.anyString());
			
			
			//when(parkingServiceImpl.findVehicle(Mockito.anyString())).thenReturn(null);
			//when(_IPersistenceRepository.findAll()).thenReturn(null);
			//doReturn(false).when(parkingServiceImpl).completeVehicle("");
			//Mockito.doNothing().when(parkingServiceImpl).getAllVehicles();
			//when(parkingServiceImpl.completeVehicle(Mockito.anyString())).thenReturn(false);
			
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert
			System.out.println(e.getMessage());
			assertEquals(ParkingServiceImpl.PARKING_COMPLETE_EXCEPTION, e.getMessage());
		}
	}

}