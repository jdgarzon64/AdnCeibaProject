package com.ceiba.AdnProject.unitTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		//_IPersistenceRepository = mock(IPersistenceRepository.class);
		//_IPaymentRepository = mock(IPaymentRepository.class);
		//_IVehicleFactory = mock(IVehicleFactory.class);
		//parkingServiceImpl = mock(ParkingServiceImpl.class);
		dataBuilderTest = mock(ParkingDataBuilderTest.class);

		this.dataBuilderTest = new ParkingDataBuilderTest();
		this.parkingServiceImpl = new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository,
				_IVehicleFactory);
	}

	@Test
	public void saveCarTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			//when(_IPersistenceRepository.save(dataBuilderTest.createParkingCar()))
					//.thenReturn(dataBuilderTest.createParkingCar());
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
}
