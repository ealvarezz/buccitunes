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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Featured_Album_Artist_Requested",
		joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
	private List<Artist> featuredArtists;
	
	private boolean isASingle;
	
	private Date releaseDate;
	
	private String label;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String artwork;
	
	private String artworkPath;
	
	private Date dateCreated;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Genre_Requested_Album",
		joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
	private List<Genre> genres;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="album_id")
	@JsonIgnoreProperties(value = "album")
	private List<RequestedSong> songs;
	
	private String comments;
	
	@OneToOne
    @JoinColumn(name = "requested_artist_id")
	private ArtistUser requester;
	
	public RequestedAlbum() {
		this.dateCreated = new Date();
	}
	
	public RequestedAlbum(int id, String title, Artist primaryArtist, List<Artist> featuredArtists, boolean isASingle,
			Date releaseDate, String label, String artwork, List<Genre> genres, List<RequestedSong> songs,
			String comments, ArtistUser user) {
		this.id = id;
		this.title = title;
		this.primaryArtist = primaryArtist;
		this.featuredArtists = featuredArtists;
		this.isASingle = isASingle;
		this.releaseDate = releaseDate;
		this.label = label;
		this.artwork = artwork;
		this.genres = genres;
		this.songs = songs;
		this.comments = comments;
		this.requester = user;
		this.dateCreated = new Date();
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

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

	public boolean getIsASingle() {
		return isASingle;
	}

	public void setIsASingle(boolean isASingle) {
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

	public ArtistUser getRequester() {
		return requester;
	}

	public void setRequester(ArtistUser user) {
		this.requester = user;
		
		if(songs != null) {
			for (RequestedSong song : songs) {
				song.setRequester(user);
			}
		}
	}

	public Artist getPrimaryArtist() {
		return primaryArtist;
	}

	public void setPrimaryArtist(Artist primaryArtist) {
		this.primaryArtist = primaryArtist;
		
		if(songs != null) {
			for (RequestedSong song : songs) {
				song.setArtist(primaryArtist);
			}
		}
	}
	
	public void setArtistRequester(ArtistUser user) {
		this.requester = user;
		this.primaryArtist = user.getArtist();
		
		if(songs != null) {
			for (RequestedSong song : songs) {
				song.setRequester(user);
				song.setArtist(user.getArtist());
			}
		}
	}

	public String getArtwork() {
		return artwork;
	}

	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}

	public String getArtworkPath() {
		return artworkPath;
	}

	public void setArtworkPath(String artworkPath) {
		this.artworkPath = artworkPath;
	}
}
