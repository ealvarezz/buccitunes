package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.User;

@NoRepositoryBean
public interface BaseUserRepository< T extends User> extends CrudRepository<T, String> {

	public List<T> findByName(String name);
	
	//@Query(value="CALL isFollowing(:email);", nativeQuery = true)
	@Procedure
	public Boolean isFollowing(String following, String followed);
	
}
