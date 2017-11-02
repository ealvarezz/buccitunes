package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Artist;
import com.buccitunes.model.User;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{

}
