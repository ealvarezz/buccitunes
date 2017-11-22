package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.miscellaneous.QueryHelper;
import com.buccitunes.model.Playlist;

@Transactional
public interface PlaylistRepository extends BaseMusicCollectionRepository<Playlist>, CrudRepository<Playlist, Integer> {

	@Query(value=""
			+ "select m.*, p.* "
			+ "from playlist p "
			+ "join music_collection m ON m.id = p.id "
			+ "join stat_cache stats on stats.id = m.stats_id "
			+ "where g.id = :genreId "
			+ "\n#pageable\n ", nativeQuery = true)
	public List<Playlist> getTopPlaylistOfAllTime();
	
	/*
	@Query(value=""
			+ "select p.*, m.*, " + QueryHelper.SELECT_PLAY_COUNTS
			+ "from playlist p "
			+ "join music_collection m on m.id = p.id "
			+ "join music_collection_song ms on ms.music_collection_id = m.id "
			+ "join song s on s.id = ms.song_id "
			+  QueryHelper.PLAY_COUNTS_BY_TIME_QUERY
			+ "group by a.id, m.title "
			+ "\n#pageable\n ", nativeQuery = true)*/
	//We need a playlist_plays table
	public List<Playlist> getTopPlaylistByWeeks(@Param("timeAgo") String timeAgo, Pageable page); 
}
