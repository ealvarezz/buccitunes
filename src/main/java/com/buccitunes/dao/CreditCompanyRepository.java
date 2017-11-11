package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.CreditCompany;

public interface CreditCompanyRepository extends CrudRepository<CreditCompany, Integer> {
	public CreditCompany findByName(String name);
}
