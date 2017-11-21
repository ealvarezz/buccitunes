package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.Song;

public interface SongRepository extends CrudRepository<Song, Integer>{
	
	@Query(value=""
			+ "select s.name, s.id, count(s.name) as song_count "
			+ "from song_plays sp "
			+ "join song s ON s.id = sp.song_id "
			+ "where sp.date_played <= NOW() and sp.date_played >= CURDATE()-7 "
			+ "group by s.name, s.id "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Song> topSongsOfTheWeek(Pageable page);
	
	@Query(value=""
			+ "select s.*, a.* "
			+ "from artist a "
			+ "join song s on s.owner_id = a.id "
			+ "join stat_cache stats on stats.id = s.stats_id "
			+ "where a.id = :artistId "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Song> getTopSongsOfAllTimeByArtist(@Param("artistId") int artistId, Pageable page);
	
	@Modifying
	@Query(value="SELECT SG.*\n" + 
			"FROM (SELECT S.id, COUNT(S.id) AS song_count FROM buccidb2.song_plays PS, buccidb2.song S\n" + 
			"WHERE S.id = PS.song_id AND PS.DATE_PLAYED > NOW() - INTERVAL 2 WEEK\n" + 
			"GROUP BY (S.id)) as SS, buccidb2.song SG WHERE SG.id = SS.id\n" + 
			"ORDER BY(song_count) DESC\n" + 
			"LIMIT 50", nativeQuery = true)
	public List<Song> getCurrentTopSongs();	
}
