package com.buccitunes.jsonmodel;

import java.util.List;

import com.buccitunes.model.Playlist;
import com.buccitunes.model.Song;

public class PlaylistPage {
	private Playlist playlist;
	private List<Song> songsToAdd;
	private List<Song> songsToRemove;
	
	public PlaylistPage() {
		
	}
	
	public PlaylistPage(Playlist playlist, List<Song> songsToAdd, List<Song> songsToRemove) {
		super();
		this.playlist = playlist;
		this.songsToAdd = songsToAdd;
		this.songsToRemove = songsToRemove;
	}
	
	public Playlist getPlaylist() {
		return playlist;
	}
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	public List<Song> getSongsToAdd() {
		return songsToAdd;
	}
	public void setSongsToAdd(List<Song> songsToAdd) {
		this.songsToAdd = songsToAdd;
	}
	public List<Song> getSongsToRemove() {
		return songsToRemove;
	}
	public void setSongsToRemove(List<Song> songsToRemove) {
		this.songsToRemove = songsToRemove;
	}
	
	
}
