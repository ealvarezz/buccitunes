package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
}
