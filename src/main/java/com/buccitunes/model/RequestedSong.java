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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="RequestedSong")
public class RequestedSong{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private int duration;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "album_id")
	private RequestedAlbum album;
	
	@OneToOne
    @JoinColumn(name = "owner_id")
	private Artist owner;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Featured_Artist_Requested",
		joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
	private List<Artist> featuredArtists;
	
	private String audioPath;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String audio;
	
	@ManyToOne
    @JoinColumn(name = "mime_id")
	private MimeType mimeType;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Genre_Requested_Song",
		joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
	private List<Genre> genres;
	
	private String picturePath;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String picture;
	
	private boolean explicit;	

	private String comments;
	
	private Date dateCreated;
	
	@OneToOne
    @JoinColumn(name = "requested_artist_id")
	private ArtistUser requester;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lyric_id")
	private RequestedLyrics lyrics;

	public RequestedSong() {
		this.dateCreated = new Date();
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Artist getOwner() {
		return owner;
	}

	public void setOwner(Artist owner) {
		this.owner = owner;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public Artist getArtist() {
		return owner;
	}

	public void setArtist(Artist artist) {
		this.owner = artist;
	}

	public List<Artist> getFeatures() {
		return featuredArtists;
	}

	public void setFeatures(List<Artist> features) {
		this.featuredArtists = features;
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
	
	public boolean getExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}

	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
	}

	public RequestedLyrics getLyrics() {
		return lyrics;
	}

	public void setLyrics(RequestedLyrics lyrics) {
		this.lyrics = lyrics;
	}


	public RequestedAlbum getAlbum() {
		return album;
	}


	public void setAlbum(RequestedAlbum album) {
		this.album = album;
	}


	public String getAudio() {
		return audio;
	}


	public void setAudio(String audio) {
		this.audio = audio;
	}
	
	
	
	
}
