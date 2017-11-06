package com.buccitunes.model;

import java.io.File;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.buccitunes.constants.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="SONG")
public class Song {
	
	@Id
	private int id;
	private String name;
	private int duration;
	private int rating;
	private Artist artist ;
	@JsonIgnore
	private List<Artist>features;
	private MimeType mimeType;
	private boolean isExplicit;
	private List<Genre> genres;
	private String picturePath;
	private String audioPath;
	private StatCache stats;
	
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public List<Artist> getFeatures() {
		return features;
	}
	public void setFeatures(List<Artist> features) {
		this.features = features;
	}
	public File getAudioAsFile() {
		return new File("");
	}
	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}
	public boolean isExplicit() {
		return isExplicit;
	}
	public void setExplicit(boolean isExplicit) {
		this.isExplicit = isExplicit;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public StatCache getStats() {
		return stats;
	}
	public void setStats(StatCache stats) {
		this.stats = stats;
	}
	
	
}
