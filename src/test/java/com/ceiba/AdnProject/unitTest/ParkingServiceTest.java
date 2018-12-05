package com.ceiba.AdnProject.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.context.junit4.SpringRunner;
import com.ceiba.AdnProject.dataBuilderTest.ParkingDataBuilderTest;
import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.exception.ParkingException;
import com.ceiba.AdnProject.factory.IVehicleFactory;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;
import com.ceiba.AdnProject.repository.IPaymentRepository;
import com.ceiba.AdnProject.repository.IPersistenceRepository;
import com.ceiba.AdnProject.service.ParkingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
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
		parkingServiceImpl = mock(ParkingServiceImpl.class);
		dataBuilderTest = mock(ParkingDataBuilderTest.class);
		parkingServiceImpl = spy(
				new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
		this.dataBuilderTest = new ParkingDataBuilderTest();
	}

	@Test
	public void saveCarTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createCar());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilderTest.LICENCE_CAR, parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveMotorcyclePlusEngineTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createMotorCyclePlusEngine());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilderTest.LICENCE_MOTORCYCLE_PLUS,
					parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveMotorcycleTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("qax", "0");
			when(_IVehicleFactory.createVehicle(dto)).thenReturn(dataBuilderTest.createMotorCycle());
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			when(_IPersistenceRepository.findAll()).thenReturn(null);
			// act
			Parking parking = parkingServiceImpl.saveVehicle(dto);
			// Assert
			assertEquals(ParkingDataBuilderTest.LICENCE_MOTORCYCLE,
					parking.getVehicle().getLicenceNumber().toUpperCase());
		} catch (ParkingException e) {
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
			parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert
			assertEquals(ParkingServiceImpl.VEHICLE_REGISTERED_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void parkingCompleteExceptionTest() throws ParkingException {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createCar()).when(_IVehicleFactory).createVehicle(dto);
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			Mockito.lenient().doReturn(null).when(_IPersistenceRepository).findAll();
			Mockito.doReturn(false).when(parkingServiceImpl).completeVehicle(Mockito.anyString());
			// act
			parkingServiceImpl.saveVehicle(dto);
		} catch (ParkingException e) {
			// Assert;
			assertEquals(ParkingServiceImpl.PARKING_COMPLETE_EXCEPTION, e.getMessage());
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
			assertEquals(ParkingServiceImpl.PATTERN_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void verifyDayTest() {
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
	public void generatePaymentCarTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingCar()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			System.out.println("precio " + payment.getTotalPrice());
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void generatePaymentMotorcycleTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingMotorcycle()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			System.out.println("precio " + payment.getTotalPrice());
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void generatePaymentMotorcyclePlusTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(dataBuilderTest.createParkingMotorcyclePlus()).when(parkingServiceImpl)
					.findVehicle(Mockito.anyString());
			// Act
			Payment payment = parkingServiceImpl.generatePayment(dto);
			// Assert
			System.out.println("precio " + payment.getTotalPrice());
			assertNotNull(payment);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void vehicleUnknowExceptionTest() {
		try {
			// Arrange
			InputDTO dto = new InputDTO("", "");
			ParkingServiceImpl parkingServiceImpl = spy(
					new ParkingServiceImpl(_IPersistenceRepository, _IPaymentRepository, _IVehicleFactory));
			Mockito.doReturn(null).when(parkingServiceImpl).findVehicle(Mockito.anyString());
			// Act
			parkingServiceImpl.generatePayment(dto);
		} catch (ParkingException e) {
			System.out.println(e.getMessage());
			// Assert
			assertEquals(ParkingServiceImpl.VEHICLE_UNKNOW, e.getMessage());
		}
	}

}