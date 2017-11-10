package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Integer> {

}
