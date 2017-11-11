package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.RequestedArtist;

public interface RequestedArtistRepository extends CrudRepository<RequestedArtist, Integer> {

}
