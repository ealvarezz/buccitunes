package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class MusicCollection {
	private int id;
	private String title;
	private List<Song> songs;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date dateCreated;
	private String artworkPath;
	private StatCache stats;
	private List<User> followers;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getArtworkPath() {
		return artworkPath;
	}
	public void setArtworkPath(String artworkPath) {
		this.artworkPath = artworkPath;
	}
	public StatCache getStats() {
		return stats;
	}
	public void setStats(StatCache stats) {
		this.stats = stats;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	public void addSong(Song song) {
		
	}
	
	public void removeSong(Song song) {
		
	}
	
}
