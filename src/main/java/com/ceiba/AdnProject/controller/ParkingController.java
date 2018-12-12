package com.ceiba.adnproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.adnproject.model.Payment;
import com.ceiba.adnproject.constants.ValidMessageResponse;
import com.ceiba.adnproject.dto.InputDTO;
import com.ceiba.adnproject.dto.ParkingResponseDTO;
import com.ceiba.adnproject.dto.PaymentResponseDTO;
import com.ceiba.adnproject.model.Parking;
import com.ceiba.adnproject.service.IParkingService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
public class ParkingController {

	@Autowired
	private IParkingService iParkingService;

	public ParkingController(IParkingService parkingService) {
		super();
		iParkingService = parkingService;
	}

	@PostMapping("/save")
	public ResponseEntity<ParkingResponseDTO> saveVehicle(@RequestBody(required = true) InputDTO object) {
		ParkingResponseDTO response;
		try {
			Parking parking = iParkingService.saveVehicle(object);
			response = new ParkingResponseDTO(ValidMessageResponse.VEHICLE_REGISTERED, parking);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ParkingResponseDTO(e.getMessage(), new Parking()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/payment")
	public ResponseEntity<PaymentResponseDTO> generatePayment(@RequestBody(required = true) InputDTO object) {
		PaymentResponseDTO response;
		try {
			Payment payment = iParkingService.generatePayment(object);
			response = new PaymentResponseDTO(ValidMessageResponse.PAYMENT_REGISTERED, payment);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new PaymentResponseDTO(e.getMessage(), new Payment()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public List<Parking> getAllVehicles() {
		return iParkingService.getAllParkings();
	}

}
