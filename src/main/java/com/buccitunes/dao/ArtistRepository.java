package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.User;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	
	public Artist findByName(String name);
}
