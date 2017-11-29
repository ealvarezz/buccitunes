package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.SongStatCache;

@Transactional
public interface SongStatCacheRepository extends BaseStatCacheRepository<SongStatCache>, CrudRepository<SongStatCache, Integer> {

}
