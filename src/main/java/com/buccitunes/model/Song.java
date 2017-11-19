package com.buccitunes.model;

import java.io.File;
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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="SONG")
public class Song {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private int duration;
	private int rating;
	private boolean isExplicit; // change to explicit, rob kelly doesn't like verbs in variables
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
	private Artist owner;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "featured_artists",
		joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
	private List<Artist>featuredArtists;
	
	@ManyToOne
    @JoinColumn(name = "mime_id")
	private MimeType mimeType;
	
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "genre_song",
		joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Genre> genres;
	private String picturePath;
	private String audioPath;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id")
	private StatCache stats;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "lyric_id")
	private Lyrics lyrics;
	
	public Song(){}
	
	public Song(RequestedSong song) {
		this.name = song.getName();
		this.duration = song.getDuration();
		this.owner = song.getArtist();
		this.featuredArtists = song.getFeatures();
		this.isExplicit = song.getIsExplicit();
		this.genres = song.getGenres();
		this.mimeType = song.getMimeType();
		this.picturePath = song.getPicturePath();
		this.audioPath = song.getAudioPath();
		this.lyrics = song.getLyrics();
		this.stats = new StatCache();
	}
	
	public String getAudioPath() {
		return audioPath;
	}
	
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
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
	
	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}
	
	public Artist getOwner() {
		return owner;
	}
	
	public void setOwner(Artist owner) {
		this.owner = owner;
	}
	
	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
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
	
	public boolean getIsExplicit() {
		return isExplicit;
	}
	
	public void setIsExplicit(boolean isExplicit) {
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

	public Lyrics getLyrics() {
		return lyrics;
	}
	
	public void setLyrics(Lyrics lyrics) {
		this.lyrics = lyrics;
	}
	
	
	

}
