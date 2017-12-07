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
	@Query(value="INSERT INTO buccidb2.artist_user(email,artist_id,create_date) VALUES (:newEmail,:newArtist,NOW())", nativeQuery = true)
	public void upgradeToArtist(@Param("newEmail")String newEmail, @Param("newArtist")int newArtist);
	/*
	@Query(value="call upgradeToArtist(:newEmail,:newArtist,:newTier,:newRole);", nativeQuery = true) 
	public ArtistUser upgradeToArtist(@Param("newEmail") String newEmail, @Param("newArtist")int newArtist, @Param("newTier")int newTier,@Param("newRole")int newRole);
	*/
}
