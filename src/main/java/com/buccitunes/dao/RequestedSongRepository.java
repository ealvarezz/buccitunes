package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.RequestedSong;

public interface RequestedSongRepository extends CrudRepository<RequestedSong, Integer> {

}
