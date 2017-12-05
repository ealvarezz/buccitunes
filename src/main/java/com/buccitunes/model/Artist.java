package com.buccitunes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="ARTIST")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id",
//		  scope = Artist.class)
public class Artist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@Column(columnDefinition = "VARCHAR(5000)")
	private String biography;

	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String avatar;
	
	String avatarPath;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "primaryArtist", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "primaryArtist")
	private List<Album> albums;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "followingArtists")
	private List<User> followers;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "featuredArtists")
	private List<Song> features;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "featuredArtists")
	@JsonIgnoreProperties(value = "featuredArtists")
	private List<Concert> upcomingConcerts;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "mainStar")
	private List<Concert> organizedConcerts;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "featured_album_id", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = "primaryArtist")
	private Album featuredAlbum;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "recently_played_song_artist",
		joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id", insertable = false, updatable = false))
	@JsonIgnoreProperties(value = "owner")
	private List<Song> recentlyPlayed;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id")
	private StatCache stats;

	public Artist() {
		this.stats = new StatCache();
	}
	
	public Artist(String name, String biography) {
		this.name = name;
		this.biography = biography;
		this.stats = new StatCache();
	}
	
	public Artist(RequestedArtist requested) {
		this.name = requested.getName();
		this.biography = requested.getBiography();
		this.stats = new StatCache();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}	
}
