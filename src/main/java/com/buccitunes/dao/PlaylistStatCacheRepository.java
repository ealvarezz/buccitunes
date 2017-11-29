package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.PlaylistStatCache;

@Transactional
public interface PlaylistStatCacheRepository extends BaseStatCacheRepository<PlaylistStatCache>, CrudRepository<PlaylistStatCache, Integer>{

}
