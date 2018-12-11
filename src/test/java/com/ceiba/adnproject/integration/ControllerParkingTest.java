package com.ceiba.adnproject.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.ceiba.adnproject.controller.ParkingController;
import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.model.Parking;
import com.ceiba.adnproject.model.Payment;
import com.ceiba.adnproject.service.IParkingService;
import com.ceiba.adnproject.service.ParkingServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ContextConfiguration
public class ControllerParkingTest {
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Mock
	ParkingServiceImpl parkingServiceImpl;

	@LocalServerPort
	private int localServerPort;
	MockMvc mockMvc;
	@Mock
	private IParkingService iParkingService;
	@InjectMocks
	private ParkingController parkingController;

	String inputCar;
	String inputCarException;
	String outputSaveCar;
	Parking parkingCar;
	InputDTO dtoCar;
	InputDTO dtoCarException;
	InputDTO dtoMotorcycle;
	InputDTO dtoMotorcyclePlus;
	Gson gson;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(parkingController).build();
		parkingController = new ParkingController(iParkingService);

		SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
		gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class, sqlAdapter).setDateFormat("yyyy-MM-dd")
				.create();

		inputCar = "{\r\n" + "	\"licence\":\"1\",\r\n" + "	\"engine\":\"0\",\r\n" + "	\"type\":\"car\"\r\n" + "}";
		inputCarException = "{\r\n" + "	\"licence\":\"4\",\r\n" + "	\"engine\":\"0\",\r\n" + "	\"type\":\"car\"\r\n" + "}";
		String inputMotorcycle = "{\r\n" + "	\"licence\":\"2\",\r\n" + "	\"engine\":\"600\",\r\n"
				+ "	\"type\":\"motorcycle\"\r\n" + "}";
		String inputMotorcyclePlus = "{\r\n" + "	\"licence\":\"3\",\r\n" + "	\"engine\":\"600\",\r\n"
				+ "	\"type\":\"motorcycle\"\r\n" + "}";

		outputSaveCar = "{\r\n" + "  \"idParking\": 1,\r\n" + "  \"status\": true,\r\n" + "  \"type\": \"CAR\",\r\n"
				+ "  \"dateIn\": \"2018-12-09\",\r\n" + "  \"vehicle\": {\r\n" + "    \"idVehicle\": 1,\r\n"
				+ "    \"vehicleType\": {\r\n" + "      \"idType\": 1,\r\n" + "      \"type\": \"CAR\"\r\n"
				+ "    },\r\n" + "    \"licenceNumber\": \"1\",\r\n" + "    \"engine\": \"0\"\r\n" + "  }\r\n" + "}";
		dtoCar = gson.fromJson(inputCar, InputDTO.class);
		dtoCarException = gson.fromJson(inputCarException, InputDTO.class);
		dtoMotorcycle = gson.fromJson(inputMotorcycle, InputDTO.class);
		dtoMotorcyclePlus = gson.fromJson(inputMotorcyclePlus, InputDTO.class);
	}

	@Test
	public void generatePaymentTest() {

		try {
			this.saveParkingCarTest();
			Mockito.doReturn(new Parking()).when(parkingServiceImpl).findVehicle("");
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:" + localServerPort + "/payment");
			ResponseEntity<Payment> response = restTemplate.postForEntity(uri, dtoCar, Payment.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Test
	public void saveParkingCarTest() {
		try {
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:" + localServerPort + "/save");
			ResponseEntity<Parking> response = restTemplate.postForEntity(uri, dtoCar, Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void getAllParkingTest() { try { URI uri = new
	 * URI("http://localhost:" + localServerPort + "/getall");
	 * ResponseEntity<Parking> response = restTemplate.getForEntity(uri,
	 * Parking.class); assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	 * } catch (Exception e) { System.out.println("exception " + e.getMessage());
	 * e.printStackTrace(); } }
	 */
	@Test
	public void saveParkingMotorcycleTest() {
		try {
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:" + localServerPort + "/save");
			ResponseEntity<Parking> response = restTemplate.postForEntity(uri, dtoMotorcycle, Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void saveParkingMotorcyclePlusTest() {
		try {
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:" + localServerPort + "/save");
			ResponseEntity<Parking> response = restTemplate.postForEntity(uri, dtoMotorcyclePlus, Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void saveParkingExceptionBadRequestTest() {
		try {
			when(parkingServiceImpl.findVehicle("")).thenReturn(null);
			URI uri = new URI("http://localhost:" + localServerPort + "/save");
			ResponseEntity<Parking> response = restTemplate.postForEntity(uri, dtoCar, Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void generatePaymentBadRequestTest() {
		try {
			URI uri = new URI("http://localhost:" + localServerPort + "/payment");
			ResponseEntity<Payment> response = restTemplate.postForEntity(uri, dtoCarException, Payment.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}

	}
}
