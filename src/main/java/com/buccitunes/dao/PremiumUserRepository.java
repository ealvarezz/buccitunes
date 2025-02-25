package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;

@Transactional
public interface PremiumUserRepository extends BaseUserRepository<PremiumUser>, CrudRepository<PremiumUser, String> {
	
	@Modifying
	@Query(value="INSERT INTO buccidb2.premium_user(email,billing_id,join_date) VALUES (:newEmail,:newBilling,NOW())", nativeQuery = true)
	public void upgradeToPremium(@Param("newEmail")String newEmail, @Param("newBilling")int newBilling);
	
	@Query(value="CALL get_needed_users_to_pay();", nativeQuery = true)
	public List<PremiumUser> getNeededUsersToPay();
}
