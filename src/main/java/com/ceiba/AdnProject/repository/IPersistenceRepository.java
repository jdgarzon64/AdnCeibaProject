package com.ceiba.AdnProject.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.AdnProject.model.Parking;
@Repository
@Transactional
public interface IPersistenceRepository extends JpaRepository<Parking, Long> {
	
}
