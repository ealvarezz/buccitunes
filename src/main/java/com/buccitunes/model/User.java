package com.buccitunes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.buccitunes.constants.UserRole;
import com.buccitunes.miscellaneous.BucciPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.JoinColumn;

@Entity(name="USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	String email;
	
	String name;
	
	String password;
	
	String username;
	
	boolean inPrivateMode;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String avatar;
	
	UserRole role;
	
	String avatarPath;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "following",
	joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "email"))
	private List<User> following;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "following")
	private List<User> followers;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_following_playlist",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"))
	private List<Playlist> followingPlaylists;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_following_artist",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
	private List<Artist> followingArtists;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_saved_song",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
	private List<Song> savedSongs;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_saved_album",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
	private List<Album> savedAlbums;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_recently_played_music_collection",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"),
	inverseJoinColumns = @JoinColumn(name = "music_collection_id", referencedColumnName = "id"))
	private List<MusicCollection> recentlyPlayed;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Playlist> playlists;
	
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
	
	@JsonProperty(access = Access.WRITE_ONLY)
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
	
	public boolean isInPrivateMode() {
		return inPrivateMode;
	}
	
	public void setInPrivateMode(boolean verified) {
		this.inPrivateMode = verified;
	}
	
	public String getProfilePicturePath() {
		return avatarPath;
	}
	
	public void setProfilePicturePath(String profilePicture) {
		this.avatarPath = profilePicture;
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
	
	public void encryptPassword() {
		this.password = BucciPassword.encryptPassword(this.password);
	}
	
	public void setPasswordAndEncrypt(String password) {
		this.password = BucciPassword.encryptPassword(password);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	
	public void updateUserInfo(User user) {
		if(user.password != null) {
			this.password = BucciPassword.encryptPassword(user.password);
		}
		if(user.avatarPath != null) {
			this.avatarPath = user.avatarPath;
		}
		this.inPrivateMode = user.inPrivateMode;
	}
}
