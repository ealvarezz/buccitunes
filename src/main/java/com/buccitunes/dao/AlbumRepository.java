package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.Album;

@Transactional
public interface AlbumRepository extends BaseMusicCollectionRepository<Album>, CrudRepository<Album, Integer> {

	

	public Album findByPrimaryArtist_Name(String name);
	
	@Query(value=""
			+ "select * FROM music_collection m "
			+ "Join album a on a.id = m.id "
			+ "where Month(a.release_date) = Month(CURDATE()) and YEAR(a.release_date) = YEAR(CURDATE()) "
			+ "\n#pageable\n", nativeQuery = true)
	public List<Album> getNewReleasesOfMonth(Pageable page);
	
	
	@Query(value=""
			+ "select m.*, a.* "
			+ "from genre g "
			+ "join genre_album ga ON ga.genre_id = g.id "
			+ "join album a ON a.id = ga.album_id "
			+ "join music_collection m ON m.id = a.id "
			+ "join stat_cache stats on stats.id = m.stats_id "
			+ "where g.id = :genreId "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Album> topAlbumsByGenre(@Param("genreId") int genreId, Pageable page);
	
	
	/*
	@Query(value=""
			+ "SELECT * FROM music_collection m "
			+ "Join album a on a.id = m.id "
			+ "where Month(a.release_date) = :currentMonth", nativeQuery = true)
	public List<Album> getByReleasesMonth(@Param("currentMonth") int currentMonth);
*/
}
