package com.buccitunes.dao;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.ArtistTransaction;

public interface ArtistTransactionRepository extends CrudRepository<ArtistTransaction, Integer>{

}
