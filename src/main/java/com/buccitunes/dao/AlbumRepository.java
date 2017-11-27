package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.QueryHelper;
import com.buccitunes.model.Album;
import com.buccitunes.model.Song;
import com.buccitunes.model.Tier;
import com.buccitunes.resultset.AlbumWithStats;

@Transactional
public interface AlbumRepository extends BaseMusicCollectionRepository<Album>, CrudRepository<Album, Integer> {

	public Album findByPrimaryArtist_Name(String name);
	
	public Album findBySongs(Song song);
	
	@Query(value="CALL get_albums_by_tier_genre(:tiercode, :genre_id, :limit);", nativeQuery = true)
	public List<Album> albumsByGenreAndTierness(@Param("tiercode") int tiercode, @Param("genre_id") int genre_id, @Param("limit") int limit);
	
	@Query(value=""
			+ "select * FROM music_collection m "
			+ "Join album a on a.id = m.id "
			+ "where Month(a.release_date) = Month(CURDATE()) and YEAR(a.release_date) = YEAR(CURDATE()) "
			+ "\n#pageable\n", nativeQuery = true)
	public List<Album> getNewReleasesOfMonth(Pageable page);
	
	
	/*
	 * 	@Query(value=""
			+ "select a.*, m.*, " + QueryHelper.SELECT_PLAY_COUNTS
			+ "from genre g "
			+ "join genre_album ga ON ga.genre_id = g.id "
			+ "join album a ON a.id = ga.album_id "
			+ "join music_collection m ON m.id = a.id "
			+ "join music_collection_song ms on ms.music_collection_id = m.id "
			+ "join song s on s.id = ms.song_id "
			+ QueryHelper.PLAY_COUNTS_BY_TIME_QUERY
			+ "where g.id = :genreId "
			+ "group by a.id, m.title "
			+ "\n#pageable\n ", nativeQuery = true)
	 */
	
	
	//@Query(value="Select * from Album", nativeQuery = true)
	//public List<Album> topAlbumsByGenre(@Param("genreId") int genreId, @Param(QueryHelper.TIME_PARAM) String timeAgo, Pageable page);
	
	
/*
 * 	@Query(value=""
			+ "select a.*, m.*, " + QueryHelper.SELECT_PLAY_COUNTS
			+ "from album a "
			+ "join music_collection m on m.id = a.id "
			+ "join music_collection_song ms on ms.music_collection_id = m.id "
			+ "join song s on s.id = ms.song_id "
			+ QueryHelper.PLAY_COUNTS_BY_TIME_QUERY
			+ "group by a.id, m.title "
			+ "\n#pageable\n ", nativeQuery = true)	
 */
	
	//@Query(value="Select * from Album", nativeQuery = true)
    //public List<Album> topAlbumsOfTheWeek(@Param(QueryHelper.TIME_PARAM) String timeAgo, Pageable page);
}
