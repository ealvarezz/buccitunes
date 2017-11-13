package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="RequestedAlbum")
public class RequestedAlbum {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	
	@ManyToOne
    @JoinColumn(name = "primary_artist_id")
	private Artist primaryArtist;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Featured_Album_Artist_Requested",
		joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Artist> featuredArtists;
	
	private boolean isASingle;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	
	private String label;
	
	private String artworkPath;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String artwork;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Genre_Requested_Album",
		joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Genre> genres;
	
	//@JsonIgnore Caused a problem with saving songs to album
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Requested_Songs_For_Album",
		joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<RequestedSong> songs;
	
	private String comments;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requested_artist_id", insertable = false, updatable = false)
	private ArtistUser user;
	
	
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

	public Artist getPrimaryArtist() {
		return primaryArtist;
	}

	public void setPrimaryArtist(Artist primaryArtist) {
		this.primaryArtist = primaryArtist;
	}

	public String getArtworkPath() {
		return artworkPath;
	}

	public void setArtworkPath(String artworkPath) {
		this.artworkPath = artworkPath;
	}

	public String getArtwork() {
		return artwork;
	}

	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}
	
	
	
	
}
