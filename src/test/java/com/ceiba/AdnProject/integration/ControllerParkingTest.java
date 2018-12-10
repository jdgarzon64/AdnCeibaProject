package com.ceiba.AdnProject.integration;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.ceiba.AdnProject.controller.ParkingController;
import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;
import com.ceiba.AdnProject.service.IParkingService;
import com.ceiba.AdnProject.service.ParkingServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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
	String outputSaveCar;
	Parking parkingCar;
	InputDTO dtoCar;
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

		inputCar = "{\r\n" + "	\"licence\":\"1\",\r\n" + "	\"engine\":\"0\",\r\n" + "	\"type\":\"car\"\r\n"
				+ "}";
		String inputMotorcycle = "{\r\n" + "	\"licence\":\"1\",\r\n" + "	\"engine\":\"440\",\r\n"
				+ "	\"type\":\"motorcycle\"\r\n" + "}";
		String inputMotorcyclePlus = "{\r\n" + "	\"licence\":\"1\",\r\n" + "	\"engine\":\"600\",\r\n"
				+ "	\"type\":\"motorcycle\"\r\n" + "}";

		outputSaveCar = "{\r\n" + "  \"idParking\": 1,\r\n" + "  \"status\": true,\r\n" + "  \"type\": \"CAR\",\r\n"
				+ "  \"dateIn\": \"2018-12-09\",\r\n" + "  \"vehicle\": {\r\n" + "    \"idVehicle\": 1,\r\n"
				+ "    \"vehicleType\": {\r\n" + "      \"idType\": 1,\r\n" + "      \"type\": \"CAR\"\r\n"
				+ "    },\r\n" + "    \"licenceNumber\": \"1\",\r\n" + "    \"engine\": \"0\"\r\n" + "  }\r\n" + "}";
		dtoCar = gson.fromJson(inputCar, InputDTO.class);
		dtoMotorcycle = gson.fromJson(inputMotorcycle, InputDTO.class);
		dtoMotorcyclePlus = gson.fromJson(inputMotorcyclePlus, InputDTO.class);

		parkingCar = gson.fromJson(outputSaveCar, Parking.class);
		System.out.println("cariito " + gson.toJson(parkingCar));

	}

	
	@Test
	public void generatePaymentTest() {

		try {
			this.saveParkingTest();
			Mockito.doReturn(new Parking()).when(parkingServiceImpl).findVehicle("");
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:"+localServerPort+"/payment");
			ResponseEntity<Payment> response = restTemplate.postForEntity(uri, dtoCar,
					Payment.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void saveParkingTest() {
		try {
			when(parkingServiceImpl.findVehicle("")).thenReturn(new Parking());
			URI uri = new URI("http://localhost:"+localServerPort+"/save");
			ResponseEntity<Parking> response = restTemplate.postForEntity(uri, dtoCar,
					Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void getAllParkingTest() {
		try {
			URI uri = new URI("http://localhost:"+localServerPort+"/getall");
			ResponseEntity<Parking> response = restTemplate.getForEntity(uri,Parking.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}
	}

}
