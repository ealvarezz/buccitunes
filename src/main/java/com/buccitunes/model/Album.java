package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.buccitunes.constants.Genre;

public class Album extends MusicCollection {
	private Artist primaryArtist;
	private List<Artist> featuredArtists;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	private String label;
	private List<Genre> genres;
	private boolean isPublic;
	
	public Artist getPrimaryArtist() {
		return primaryArtist;
	}
	public void setPrimaryArtist(Artist primaryArtist) {
		this.primaryArtist = primaryArtist;
	}
	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}
	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
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
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}
