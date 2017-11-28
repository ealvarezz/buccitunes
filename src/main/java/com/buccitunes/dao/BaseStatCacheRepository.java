package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.buccitunes.model.StatCache;

@NoRepositoryBean
public interface BaseStatCacheRepository < T extends StatCache> extends CrudRepository<T, Integer> {

}
