package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.SongPlays;

public interface SongPlaysRepository extends CrudRepository<SongPlays, Integer> {

	
}
