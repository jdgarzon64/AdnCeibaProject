package com.ceiba.AdnProject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.AdnProject.model.Parking;
@Repository
public interface IPersistenceRepository extends CrudRepository<Parking, Long> {
	// @Query("delete from vehicle where vehicle.licence_number = :licence ")
	// public boolean deleteParking(String licence);
}
