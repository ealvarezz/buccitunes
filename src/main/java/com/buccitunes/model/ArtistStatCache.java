package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;

@Entity(name="ARTIST_STAT_CACHE")
public class ArtistStatCache extends StatCache{
	
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="artist_artist_stat_cache", 
	joinColumns = @JoinColumn(name="artist_stat_cache_id", referencedColumnName = "id", insertable = false, updatable = false),
	inverseJoinColumns = @JoinColumn(name="artist_id", referencedColumnName = "id", insertable = false, updatable = false))
	@JsonIgnoreProperties(value = "albums")
	private Artist artist;
	
	public ArtistStatCache() {}

	

	public ArtistStatCache(int totalPlays, int monthlyPlays, double totalRevenue, double monthlyRevenue, int rank, Artist artist) {
		super(totalPlays, monthlyPlays, totalRevenue, monthlyRevenue, rank);
		this.artist = artist;
	}


	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}
