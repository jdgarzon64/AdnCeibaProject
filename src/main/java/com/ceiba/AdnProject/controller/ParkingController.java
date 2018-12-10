package com.ceiba.AdnProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Payment;
import com.ceiba.AdnProject.service.IParkingService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
public class ParkingController {

	@Autowired
	private IParkingService IParkingService;
	
	public ParkingController(com.ceiba.AdnProject.service.IParkingService iParkingService) {
		super();
		IParkingService = iParkingService;
	}

	@PostMapping("/save")
	public ResponseEntity<Parking> saveVehicle(@RequestBody(required = true) InputDTO object) {
		try {
			Parking response = IParkingService.saveVehicle(object);
			return new ResponseEntity<Parking>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Parking>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/payment")
	public ResponseEntity<Payment> generatePayment(@RequestBody(required = true) InputDTO object) {
		try {
			Payment response = IParkingService.generatePayment(object);
			return new ResponseEntity<Payment>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Payment>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public List<Parking> getAllVehicles() {
		return IParkingService.getAllParkings();
	}
}
