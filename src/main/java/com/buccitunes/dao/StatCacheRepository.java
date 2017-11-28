package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.StatCache;

@Transactional
public interface StatCacheRepository extends BaseStatCacheRepository<StatCache>, CrudRepository<StatCache, Integer> {

}
