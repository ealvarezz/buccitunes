package com.buccitunes.model;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="ARTIST")
public class Artist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String biography;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "primaryArtist", cascade = CascadeType.ALL)
	private List<Album> albums;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "followingArtists")
	private List<User> followers;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "featuredArtists")
	private List<Song> features;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "featuredArtists")
	private List<Concert> upcomingConcerts;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "featured_album_id", insertable = false, updatable = false)
	private Album featuredAlbum;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "recently_played_song_artist",
		joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Song> recentlyPlayed;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id", insertable = false, updatable = false)
	private StatCache stats;
	
	
	

	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public List<Song> getFeatures() {
		return features;
	}
	public void setFeatures(List<Song> features) {
		this.features = features;
	}
	public List<Concert> getUpcomingConcerts() {
		return upcomingConcerts;
	}
	public void setUpcomingConcerts(List<Concert> upcomingConcerts) {
		this.upcomingConcerts = upcomingConcerts;
	}
	public Album getFeaturedAlbum() {
		return featuredAlbum;
	}
	public void setFeaturedAlbum(Album featuredAlbum) {
		this.featuredAlbum = featuredAlbum;
	}
	public List<Song> getRecentlyPlayed() {
		return recentlyPlayed;
	}
	public void setRecentlyPlayed(List<Song> recentlyPlayed) {
		this.recentlyPlayed = recentlyPlayed;
	}
	public StatCache getStats() {
		return stats;
	}
	public void setStats(StatCache stats) {
		this.stats = stats;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
}
