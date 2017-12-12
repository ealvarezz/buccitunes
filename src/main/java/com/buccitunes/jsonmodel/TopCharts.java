package com.buccitunes.jsonmodel;

import java.util.List;

import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.Song;

public class TopCharts {

	private List<Album> topAlbums;
	private List<Song> topSongs;
	private List<Artist> topArtists;
	
	public TopCharts(List<Album> topAlbums, List<Song> topSongs, List<Artist> topArtists) {
		super();
		this.topAlbums = topAlbums;
		this.topSongs = topSongs;
		this.topArtists = topArtists;
	}

	public List<Album> getTopAlbums() {
		return topAlbums;
	}

	public void setTopAlbums(List<Album> topAlbums) {
		this.topAlbums = topAlbums;
	}

	public List<Song> getTopSongs() {
		return topSongs;
	}

	public void setTopSongs(List<Song> topSongs) {
		this.topSongs = topSongs;
	}

	public List<Artist> getTopArtists() {
		return topArtists;
	}

	public void setTopArtists(List<Artist> topArtists) {
		this.topArtists = topArtists;
	}
	
	
}
