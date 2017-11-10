package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.RequestedConcert;

public interface RequestedConcertRepository extends CrudRepository<RequestedConcert, Integer>{

}
