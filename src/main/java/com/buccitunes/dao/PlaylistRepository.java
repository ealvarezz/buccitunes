package com.buccitunes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Playlist;

@Transactional
public interface PlaylistRepository extends BaseMusicCollectionRepository<Playlist>, CrudRepository<Playlist, Integer> {

	// TODO make query
	List<Playlist> getTopPlaylistOfAllTime();

	// TODO make query
	List<Playlist> topPlaylistsByGenre(int genreId);

}
