package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Playlist;
import com.buccitunes.model.SongPlays;

public interface SongPlaysRepository extends CrudRepository<SongPlays, Integer> {


	
}
