package com.buccitunes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.buccitunes.miscellaneous.BucciPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity(name="USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	String email;
	String name;
	String password;
	String username;
	boolean verified;
	String profilePicture;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "following",
	joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "email"))
	private List<User> following;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "following")
	private List<User> followers;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_following_playlist",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"))
	private List<Playlist> followingPlaylists;
	
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_following_artist",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
	private List<Artist> followingArtists;
	
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_saved_song",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
	private List<Song> savedSongs;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_saved_album",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
	private List<Album> savedAlbums;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_recently_played_music_collection",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "music_collection_id", referencedColumnName = "id"))
	private List<MusicCollection> recentlyPlayed;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Playlist> playlists;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_collaborating_playlist",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"))
	private List<Playlist> collaboratingPlaylitst;


	public User(){}
	
	public User(String email, String name, String password, String username) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public List<User> getFollowing() {
		return following;
	}
	public void setFollowing(List<User> following) {
		this.following = following;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public List<Playlist> getFollowingPlaylists() {
		return followingPlaylists;
	}
	public void setFollowingPlaylists(List<Playlist> followingPlaylists) {
		this.followingPlaylists = followingPlaylists;
	}
	public List<Artist> getFollowingArtists() {
		return followingArtists;
	}
	public void setFollowingArtists(List<Artist> followingArtists) {
		this.followingArtists = followingArtists;
	}
	public List<Song> getSavedSongs() {
		return savedSongs;
	}
	public void setSavedSongs(List<Song> savedSongs) {
		this.savedSongs = savedSongs;
	}
	public List<Album> getSavedAlbums() {
		return savedAlbums;
	}
	public void setSavedAlbums(List<Album> savedAlbums) {
		this.savedAlbums = savedAlbums;
	}
	public List<MusicCollection> getRecentlyPlayed() {
		return recentlyPlayed;
	}
	public void setRecentlyPlayed(List<MusicCollection> recentlyPlayed) {
		this.recentlyPlayed = recentlyPlayed;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	public List<Playlist> getCollaboratingPlaylitst() {
		return collaboratingPlaylitst;
	}
	public void setCollaboratingPlaylitst(List<Playlist> collaboratingPlaylitst) {
		this.collaboratingPlaylitst = collaboratingPlaylitst;
	}
	
	public void encryptAndSetPassword(String password) {
		this.password = BucciPassword.encryptPassword(password);
	}


}
