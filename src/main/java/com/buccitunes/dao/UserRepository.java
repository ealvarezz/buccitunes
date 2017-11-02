package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	public List<User> findByName(String name);
	
	
	
}
