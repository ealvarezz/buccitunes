package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>  {

}
