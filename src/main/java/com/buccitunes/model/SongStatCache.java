package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="SONG_STAT_CACHE")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "song")
public class SongStatCache extends StatCache{

	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="song_song_stat_cache", 
	joinColumns = @JoinColumn(name="song_stat_cache_id", referencedColumnName = "id", insertable = false, updatable = false),
	inverseJoinColumns = @JoinColumn(name="song_id", referencedColumnName = "id", insertable = false, updatable = false))
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
