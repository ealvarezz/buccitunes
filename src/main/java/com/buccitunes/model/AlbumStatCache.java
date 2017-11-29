package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

@Entity(name="ALBUM_STAT_CACHE")
public class AlbumStatCache extends StatCache{


	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="album_album_stat_cache", 
	joinColumns = @JoinColumn(name="album_stat_cache_id", referencedColumnName = "id", insertable = false, updatable = false),
	inverseJoinColumns = @JoinColumn(name="album_id", referencedColumnName = "id", insertable = false, updatable = false))
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
