package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name="SONG_STAT_CACHE")
public class SongStatCache extends StatCache{

	
	@OneToOne(mappedBy = "songStats")
	private Song song;
	
	public SongStatCache() {}
	
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

}
