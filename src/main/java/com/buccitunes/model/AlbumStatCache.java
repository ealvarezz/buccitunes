package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name="ALBUM_STAT_CACHE")
public class AlbumStatCache extends StatCache{


	@OneToOne(mappedBy = "albumStats")
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
