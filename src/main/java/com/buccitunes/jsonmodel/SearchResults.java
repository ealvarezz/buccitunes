package com.buccitunes.jsonmodel;

import java.util.List;

import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.Song;

public class SearchResults {
	
	private List<Song> songResults;
	private List<Artist> artistResults;
	private List<Album> albumResults;
	private List<Playlist> playlistResults;
	
	public SearchResults(List<Song> songResults, List<Artist> artistResults, List<Album> albumResults,
			List<Playlist> playlistResults) {
		super();
		this.songResults = songResults;
		this.artistResults = artistResults;
		this.albumResults = albumResults;
		this.playlistResults = playlistResults;
	}

	public List<Song> getSongResults() {
		return songResults;
	}

	public void setSongResults(List<Song> songResults) {
		this.songResults = songResults;
	}

	public List<Artist> getArtistResults() {
		return artistResults;
	}

	public void setArtistResults(List<Artist> artistResults) {
		this.artistResults = artistResults;
	}

	public List<Album> getAlbumResults() {
		return albumResults;
	}

	public void setAlbumResults(List<Album> albumResults) {
		this.albumResults = albumResults;
	}

	public List<Playlist> getPlaylistResults() {
		return playlistResults;
	}

	public void setPlaylistResults(List<Playlist> playlistResults) {
		this.playlistResults = playlistResults;
	}
	
	
}
