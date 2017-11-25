package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
	
	@Query(value="CALL topGenresForCurrentUser(:email);", nativeQuery = true)
	List<Genre> topGenresForCurrentUsers(String email);
}
