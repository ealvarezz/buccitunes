package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.MusicCollection;

@Transactional
public interface MusicCollectionRepository extends BaseMusicCollectionRepository<MusicCollection>, CrudRepository<MusicCollection, Integer> {

}
