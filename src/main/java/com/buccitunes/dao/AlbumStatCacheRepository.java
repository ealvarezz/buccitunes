package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.AlbumStatCache;

@Transactional
public interface AlbumStatCacheRepository extends BaseStatCacheRepository<AlbumStatCache>, CrudRepository<AlbumStatCache, Integer>{

}
