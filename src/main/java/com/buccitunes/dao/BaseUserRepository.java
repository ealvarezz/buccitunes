package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.buccitunes.model.User;

@NoRepositoryBean
public interface BaseUserRepository< T extends User> extends CrudRepository<T, String> {

	public List<T> findByName(String name);
}
