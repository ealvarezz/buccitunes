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
	
	@Query(value="CALL get_related_artists(:artistId);", nativeQuery = true)
	public List<Artist> getRelatedArtist(@Param("artistId") int artistId);
	
	@Query(value="CALL search_artist(:name);", nativeQuery = true)
	public List<Artist> searchArtists(@Param("name") String name);
	
	@Query(value="CALL get_top_artists();", nativeQuery = true)
	public List<Artist> topArtists();
}
