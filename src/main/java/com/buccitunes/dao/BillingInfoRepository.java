package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.BillingInfo;

public interface BillingInfoRepository extends CrudRepository<BillingInfo, Integer> {
	
}
