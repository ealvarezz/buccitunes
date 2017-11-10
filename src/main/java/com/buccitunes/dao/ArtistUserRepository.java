package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.buccitunes.model.ArtistUser;

@Transactional
public interface ArtistUserRepository extends BaseUserRepository<ArtistUser>, CrudRepository<ArtistUser, String> {

}
