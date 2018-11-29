package com.ceiba.AdnProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.AdnProject.dto.InputDTO;
import com.ceiba.AdnProject.dto.ResponseDTO;
import com.ceiba.AdnProject.model.Parking;
import com.ceiba.AdnProject.model.Vehicle;
import com.ceiba.AdnProject.service.IParkingService;

@CrossOrigin
@RestController
@RequestMapping(value = "/parking")
public class ParkingController {

	@Autowired
	private IParkingService _IParkingService;

	@RequestMapping("/save")
	public ResponseEntity<Parking> test(@RequestBody(required = true) InputDTO object) {
		try {
			Parking response = _IParkingService.saveVehicle(object);
			return new ResponseEntity<Parking>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Parking>(HttpStatus.BAD_REQUEST);
		}
	}
}
