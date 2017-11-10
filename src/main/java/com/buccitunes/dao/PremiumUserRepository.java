package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.PremiumUser;

@Transactional
public interface PremiumUserRepository extends BaseUserRepository<PremiumUser>, CrudRepository<PremiumUser, String> {

}
