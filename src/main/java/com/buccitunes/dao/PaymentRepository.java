package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}
