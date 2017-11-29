package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="SONG_STAT_CACHE")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "song")
public class SongStatCache extends StatCache{

	
	@OneToOne(mappedBy = "songStats")
	@JsonIgnoreProperties(value = "songStats")
	private Song song;
	
	public SongStatCache() {}
	
	public SongStatCache(int totalPlays, int monthlyPlays, double totalRevenue, double monthlyRevenue, int rank, Song song) {
		super(totalPlays, monthlyPlays, totalRevenue, monthlyRevenue, rank);
		this.song = song;
	}



	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

}
