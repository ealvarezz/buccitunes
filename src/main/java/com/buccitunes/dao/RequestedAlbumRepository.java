package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.RequestedAlbum;

public interface RequestedAlbumRepository extends CrudRepository<RequestedAlbum, Integer> {

}
