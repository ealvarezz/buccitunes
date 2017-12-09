package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Payment;
import com.buccitunes.model.PremiumUser;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
	
	public List<Payment> findByPremiumUserOrderByDateDesc(PremiumUser user);
	public Payment findTopByPremiumUserOrderByDateDesc(PremiumUser user);
	public List<Payment> findTop2ByPremiumUserOrderByDateDesc(PremiumUser user);
}
