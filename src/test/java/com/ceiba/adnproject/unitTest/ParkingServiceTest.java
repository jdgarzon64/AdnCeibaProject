package com.ceiba.adnproject.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.adnproject.constants.InvalidMessageResponse;
import com.ceiba.adnproject.dataBuilderTest.ParkingDataBuilder;
import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.exception.ParkingException;
import com.ceiba.adnproject.factory.IVehicleFactory;
import com.ceiba.adnproject.model.Parking;
import com.ceiba.adnproject.model.Payment;
import com.ceiba.adnproject.repository.IPaymentRepository;
import com.ceiba.adnproject.repository.IPersistenceRepository;
import com.ceiba.adnproject.service.ParkingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@ContextConfiguration
public class ParkingServiceTest {

	@Mock
	IPersistenceRepository _IPersistenceRepository;
	@Mock
	IPaymentRepository _IPaymentRepository;
	@Mock
	IVehicleFactory _IVehicleFactory;
	@InjectMocks
	ParkingServiceImpl parkingServiceImpl;

	ParkingDataBuilder dataBuilderTest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		parkingServiceImpl = mock(ParkingServiceImpl.class);
		dataBuilderTest = mock(ParkingDataBuilder.class);
		parkingServiceImpl = spy(
				new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
		this.dataBuilderTest = new ParkingDataBuilder();
	}

	@Test
	public void saveCarTest() {
		try {

			// Arrange
			InputDTO dto = new InputDTO("qax", "0", "");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilder.LICENCE_CAR, parking.getVehicle().getLicenceNumber().toUpperCase());

		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveMotorcyclePlusEngineTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0", "");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createMotorCyclePlusEngine());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilder.LICENCE_MOTORCYCLE_PLUS,
					parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveMotorcycleTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0", "");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createMotorCycle());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilder.LICENCE_MOTORCYCLE, parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void vehicleRegisteredExceptionTest() throws ParkingException {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			List<Parking> list = new ArrayList<Parking>();
			List<Parking> spyList = Mockito.spy(list);
			spyList.add(dataBuilderTest.createParkingCar());

			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(_IPersistenceRepository.findAll()).thenReturn(spyList);
			// act
			parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert
			assertEquals(InvalidMessageResponse.VEHICLE_REGISTERED_EXCEPTION, e.getMessage());
		}
	}

//create test motorcycle
	@Test
	public void parkingCompleteExceptionForCarTest() throws ParkingException {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createCar()).when(_IVehicleFactory).createVehicle(dto);
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			Mockito.lenient().doReturn(null).when(_IPersistenceRepository).findAll();
			Mockito.doReturn(false).when(parkingServiceImpl).isCompleteVehicle(Mockito.anyString());
			// act
			parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert;
			assertEquals(InvalidMessageResponse.PARKING_COMPLETE_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void parkingCompleteExceptionForMotorcycleTest() throws ParkingException {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createMotorcycle()).when(_IVehicleFactory).createVehicle(dto);
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			Mockito.lenient().doReturn(null).when(_IPersistenceRepository).findAll();
			Mockito.doReturn(false).when(parkingServiceImpl).isCompleteVehicle(Mockito.anyString());
			// act
			parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert;
			assertEquals(InvalidMessageResponse.PARKING_COMPLETE_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void verifyLicencePatternExceptionTest() {
		try {
			// Arange
			Mockito.doReturn(true).when(parkingServiceImpl).verifyDay();
			// Act
			parkingServiceImpl.verifyLicence("a");
		} catch (ParkingException e) {
			// Assert;
			assertEquals(InvalidMessageResponse.PATTERN_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void verifyDaySundayTest() {
		// Arrange
		GregorianCalendar c = Mockito.mock(GregorianCalendar.class);
		Mockito.when(c.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.SUNDAY);
		// act
		parkingServiceImpl.setCalendario(c);
		boolean response = parkingServiceImpl.verifyDay();
		// Assert
		assertFalse(response);
	}

	@Test
	public void verifyDayMondayTest() {
		// Arrange
		GregorianCalendar c = Mockito.mock(GregorianCalendar.class);
		Mockito.when(c.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);
		// act
		parkingServiceImpl.setCalendario(c);
		boolean response = parkingServiceImpl.verifyDay();
		// Assert
		assertFalse(response);
	}

	@Test
	public void generatePaymentCarTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingCar()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void generatePaymentMotorcycleTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingMotorcycle()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void generatePaymentMotorcyclePlusTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingMotorcyclePlus()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void vehicleUnknowExceptionTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			// Act
			parkingServiceImpl.generatePayment(dto);
		} catch (ParkingException e) {
			// Assert
			assertEquals(InvalidMessageResponse.VEHICLE_UNKNOW, e.getMessage());
		}
	}

	public void verifyMaxEngineTest() {
		ParkingServiceImpl parkingServiceImpl = spy(
				new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
		boolean response = parkingServiceImpl.verifyEngine(String.valueOf(ParkingDataBuilder.ENGINE_MOTORCYCLE_PLUS));
		assertTrue(response);
	}

	public void verifyMinEngineTest() {
		ParkingServiceImpl parkingServiceImpl = spy(
				new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
		boolean response = parkingServiceImpl.verifyEngine(String.valueOf(ParkingDataBuilder.ENGINE_MOTORCYCLE));
		assertTrue(response);
	}
}