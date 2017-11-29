package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="ARTIST_STAT_CACHE")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "artist")
public class ArtistStatCache extends StatCache{
	
	@OneToOne(mappedBy = "artistStats")
	@JsonIgnoreProperties(value = "artistStats")
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
