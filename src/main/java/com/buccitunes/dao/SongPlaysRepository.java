package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.SongPlays;

public interface SongPlaysRepository extends CrudRepository<SongPlays, Integer> {

	List<SongPlays> findBySong_Owner_Id(int id);
	
	@Query(value="SELECT SP.*\n" + 
				"FROM buccidb2.song S, buccidb2.artist A, buccidb2.song_plays SP\n" + 
				"WHERE A.id = :artistId AND S.owner_id = A.id AND SP.song_id = S.id\n" + 
				"AND SP.date_played > NOW() - INTERVAL 4 WEEK", nativeQuery = true)
	List<SongPlays> getCurrentSongPlaysByArtist(@Param("artistId") int artistId);
}
