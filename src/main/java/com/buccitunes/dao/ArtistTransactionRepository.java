package com.buccitunes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.ArtistTransaction;

public interface ArtistTransactionRepository extends CrudRepository<ArtistTransaction, Integer>{

	public List<ArtistTransaction> findByArtist_Id(int artistId);
}
