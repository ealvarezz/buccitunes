package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.buccitunes.model.MusicCollection;

@NoRepositoryBean
public interface BaseMusicCollectionRepository < T extends MusicCollection> extends CrudRepository<T, Integer> {

	
}
