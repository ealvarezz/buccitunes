package com.buccitunes.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.buccitunes.model.ArtistUser;

@Transactional
public interface ArtistUserRepository extends BaseUserRepository<ArtistUser>, CrudRepository<ArtistUser, String> {
	
	@Modifying
	@Query(value="INSERT INTO buccidb2.artist_user(email,artist_id) VALUES (:newEmail, :newArtist)", nativeQuery = true)
	public void upgradeToArtist(@Param("newEmail") String newEmail, @Param("newArtist")int newArtist);
}
