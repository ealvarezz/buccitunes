package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.Song;

public interface SongRepository extends CrudRepository<Song, Integer>{

}
