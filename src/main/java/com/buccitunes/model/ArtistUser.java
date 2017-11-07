package com.buccitunes.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="ArtistUser")
public class ArtistUser extends User {
	
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id", insertable = false, updatable = false)
	private Artist artist;

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	
	public void requestAddSong(Song song, Album album) {
		
	}
	
	public void requestAddAlbum(Album album, List<Song> songs) {
		
	}
	
	public void changeBiography(String bio) {
		
	}
	
	public List<Song> displayTopSongs() {
		List<Song> topSongs = new ArrayList<Song>();
		
		return topSongs;
	}
	
	/*
	public void displayAlbums() {
		
	}
	*/
	
}
