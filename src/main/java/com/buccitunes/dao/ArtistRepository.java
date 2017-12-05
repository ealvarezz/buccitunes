package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.buccitunes.model.Artist;


public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	
	public Artist findByName(String name);
	
	@Query(value="CALL search_artist(:name);", nativeQuery = true)
	public List<Artist> searchArtist(@Param("name") String name);
	
}
