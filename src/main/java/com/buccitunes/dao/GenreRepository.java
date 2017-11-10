package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
	
}
