package com.buccitunes.model;

import java.util.List;

public class RequestedSong {
	private int id;
	
	private String name;
	
	private int duration;
	
	private Artist artist;
	
	private List<Artist> features;
	
	private String audioPath;
	
	private MimeType mimeType;
	
	private List<Genre> genres;
	
	private String picturePath;
	
	private String comments;
	
	private ArtistUser requester;

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

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ArtistUser getRequester() {
		return requester;
	}

	public void setRequester(ArtistUser requester) {
		this.requester = requester;
	}
	
	//private RequestedAlbum albumRequestedOn;
 
}
