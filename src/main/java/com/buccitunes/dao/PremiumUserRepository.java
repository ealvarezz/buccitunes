package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.PremiumUser;

@Transactional
public interface PremiumUserRepository extends BaseUserRepository<PremiumUser>, CrudRepository<PremiumUser, String> {
	
	@Modifying
	@Query(value="INSERT INTO buccidb2.premium_user(email,billing_id) VALUES (:newEmail,:newBilling)", nativeQuery = true)
	public void upgradeToPremium(@Param("newEmail") String newEmail, @Param("newBilling")int newBilling);
}
