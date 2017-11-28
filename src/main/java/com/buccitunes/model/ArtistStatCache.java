package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name="ARTIST_STAT_CACHE")
public class ArtistStatCache extends StatCache{
	
	@OneToOne(mappedBy = "artistStats")
	private Artist artist;
	
	public ArtistStatCache() {}
	
	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}
