package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name="ALBUM_STAT_CACHE")
public class AlbumStatCache extends StatCache{


	@OneToOne(mappedBy = "albumStats")
	private Album album;

	public AlbumStatCache() {}
	
	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

}
