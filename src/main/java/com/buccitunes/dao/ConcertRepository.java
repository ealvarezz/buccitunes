package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Integer> {
	
	@Query(value="call getConcertsOfArtistId(:id);", nativeQuery = true)
	public List<Concert> getConcertsOfArtistId(int id);
}
