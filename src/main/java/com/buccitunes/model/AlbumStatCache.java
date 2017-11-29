package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="ALBUM_STAT_CACHE")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "album")
public class AlbumStatCache extends StatCache{


	@OneToOne(mappedBy = "albumStats")
	@JsonIgnoreProperties(value = "albumStats")
	private Album album;

	public AlbumStatCache() {}
	
	public AlbumStatCache(int totalPlays, int monthlyPlays, double totalRevenue, double monthlyRevenue, int rank, Album album) {
		super(totalPlays, monthlyPlays, totalRevenue, monthlyRevenue, rank);
		this.album = album;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

}
