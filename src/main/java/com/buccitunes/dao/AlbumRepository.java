package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.Album;
import com.buccitunes.model.Song;

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
	
	@Query(value=""
			+ "select a.*, m.*, sum(sp_count.song_count) as numPlays "
			+ "from album a "
			+ "join music_collection m on m.id = a.id "
			+ "join music_collection_song ms on ms.music_collection_id = m.id "
			+ "join song s on s.id = ms.song_id "
			+ "join( "
			+ "	select sp.song_id, count(sp.song_id) as song_count "
			+ "	from song_plays sp "
			+ "	where sp.date_played <= NOW() and sp.date_played >= CURDATE()-7 "
			+ "	group by sp.song_id "
			+ ") sp_count on sp_count.song_id = s.id "
			+ "group by a.id, m.title "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Album> topAlbumsOfTheWeek(Pageable page);
	
	
	@Query(value=""
			+ "select s.name, s.id, count(s.name) as song_count "
			+ "from song_plays sp "
			+ "join song s ON s.id = sp.song_id "
			+ "where sp.date_played <= NOW() and sp.date_played >= CURDATE()-7 "
			+ "group by s.name, s.id "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Song> topSongsOfTheWeek(Pageable page);
	/*
	@Query(value=""
			+ "SELECT * FROM music_collection m "
			+ "Join album a on a.id = m.id "
			+ "where Month(a.release_date) = :currentMonth", nativeQuery = true)
	public List<Album> getByReleasesMonth(@Param("currentMonth") int currentMonth);
*/
}
