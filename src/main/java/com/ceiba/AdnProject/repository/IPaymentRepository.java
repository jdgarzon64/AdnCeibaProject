package com.ceiba.adnproject.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.adnproject.model.Payment;
@Repository
@Transactional
public interface IPaymentRepository extends JpaRepository<Payment, Long>{

}
