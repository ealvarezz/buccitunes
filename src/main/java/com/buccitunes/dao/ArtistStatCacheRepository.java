package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.ArtistStatCache;

@Transactional
public interface ArtistStatCacheRepository extends BaseStatCacheRepository<ArtistStatCache>, CrudRepository<ArtistStatCache, Integer>{

}
