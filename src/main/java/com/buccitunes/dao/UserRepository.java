package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.User;

@Transactional
public interface UserRepository extends BaseUserRepository<User>, CrudRepository<User, String>{

	
	
	
}
