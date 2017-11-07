package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class RequestedAlbum {
	private int id;
	
	private String biography;
	
	private String coverArtPath;
	
	private List<Artist> featuredArtists;
	
	private boolean isASingle;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	
	private String label;
	
	private List<Genre> genres;
	
	private List<RequestedSong> songs;
	
	private String comments;
	
	private ArtistUser user;

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getCoverArtPath() {
		return coverArtPath;
	}

	public void setCoverArtPath(String coverArtPath) {
		this.coverArtPath = coverArtPath;
	}

	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}

	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
	}

	public boolean isASingle() {
		return isASingle;
	}

	public void setASingle(boolean isASingle) {
		this.isASingle = isASingle;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<RequestedSong> getSongs() {
		return songs;
	}

	public void setSongs(List<RequestedSong> songs) {
		this.songs = songs;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ArtistUser getUser() {
		return user;
	}

	public void setUser(ArtistUser user) {
		this.user = user;
	}
	
	
	
}
