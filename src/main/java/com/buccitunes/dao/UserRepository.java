package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.User;

@Transactional
public interface UserRepository extends BaseUserRepository<User>, CrudRepository<User, String>{

	@Query(value="CALL downgrade_premium_user(:userEmail,:roleId);", nativeQuery = true)
	public void downgradeToBasic(@Param("userEmail")String userEmail, @Param("roleId")int roleId);
	
	@Query(value="CALL search_user(:name);", nativeQuery = true)
	public List<User> searchUser(@Param("name") String name);
}
