package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.UserActivity;


public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {

}
