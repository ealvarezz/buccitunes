package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Song;

public interface SongRepository extends CrudRepository<Song, Integer>{

	@Modifying
	@Query(value="SELECT SG.*\n" + 
			"FROM (SELECT S.id, COUNT(S.id) AS song_count FROM buccidb2.song_plays PS, buccidb2.song S\n" + 
			"WHERE S.id = PS.song_id AND PS.DATE_PLAYED > NOW() - INTERVAL 2 WEEK\n" + 
			"GROUP BY (S.id)) as SS, buccidb2.song SG WHERE SG.id = SS.id\n" + 
			"ORDER BY(song_count) DESC\n" + 
			"LIMIT 50", nativeQuery = true)
	public List<Song> getGlobalTopSongs();
}
