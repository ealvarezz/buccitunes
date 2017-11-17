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
			+ "SELECT * FROM music_collection m "
			+ "Join album a on a.id = m.id"
			+ "where Month(a.release_date) = Month(CURDATE()) and YEAR(a.release_date) = YEAR(CURDATE())"
			+ "\n#pageable\n", nativeQuery = true)
	public List<Album> getByReleasesMonth(Pageable page);
	
	/*
	@Query(value=""
			+ "SELECT * FROM music_collection m "
			+ "Join album a on a.id = m.id "
			+ "where Month(a.release_date) = :currentMonth", nativeQuery = true)
	public List<Album> getByReleasesMonth(@Param("currentMonth") int currentMonth);
*/
}
