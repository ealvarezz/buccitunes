package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Playlist;

@Transactional
public interface PlaylistRepository extends BaseMusicCollectionRepository<Playlist>, CrudRepository<Playlist, Integer> {

}
