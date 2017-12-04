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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="PLAYLIST")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Playlist extends MusicCollection {
	
	private boolean isCollaborative;
	
	private boolean isPublic;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "playlist_song",
		joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
	@JsonIgnoreProperties(value = "owner")
	private List<Song> songs;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User owner;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "collaboratingPlaylitst")
	private List<User> collaboratives;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "followingPlaylists")
	private List<User> followers;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id")
	private StatCache stats;
	
	public Playlist(){
		super();
		this.stats = new StatCache();
	};
	
	public Playlist(List<Song> songs){
		super();
		this.songs = songs;
		this.stats = new StatCache();
	};
	

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public void addSong(Song song) {
		this.songs.add(song);
	}
	
	public void removeSong(Song song) {
		this.songs.remove(song);
	}

	public boolean isCollaborative() {
		return isCollaborative;
	}
	
	public void setCollaborative(boolean isCollaborative) {
		this.isCollaborative = isCollaborative;
	}
	
	public boolean isPublic() {
		return isPublic;
	}
	
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public List<User> getCollaboratives() {
		return collaboratives;
	}
	
	public void setCollaboratives(List<User> collaboratives) {
		this.collaboratives = collaboratives;
	}
	
	public void canEdit(User user) {
		
	}
	
	public void canView(User user) {
		
	}
	
	public List<User> getFollowers() {
		return followers;
	}
	
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public StatCache getStats() {
		return stats;
	}

	public void setStats(StatCache stats) {
		this.stats = stats;
	}
	
	public void updateInfo(Playlist playlist) {
		if(playlist.getTitle() != null || playlist.getTitle().isEmpty()) {
			super.setTitle(playlist.getTitle());
		}
		
		this.isPublic = playlist.isPublic();
		
		
	}
	
}
