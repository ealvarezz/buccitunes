package com.buccitunes.model;

import java.util.*;

public class ArtistUser extends User {
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
