package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.Album;

@Transactional
public interface AlbumRepository extends BaseMusicCollectionRepository<Album>, CrudRepository<Album, Integer> {

}
