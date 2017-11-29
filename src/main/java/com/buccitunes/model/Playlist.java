package com.buccitunes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="PLAYLIST")
public class Playlist extends MusicCollection {
	
	private boolean isCollaborative;
	
	private boolean isPublic;
	
	@ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User owner;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "collaboratingPlaylitst")
	private List<User> collaboratives;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "followingPlaylists")
	private List<User> followers;
	
	@OneToOne(mappedBy="playlist", cascade=CascadeType.ALL)
	private PlaylistStatCache playlistStatCache; 
	
	public Playlist(){
		super();
	};
	
	public PlaylistStatCache getPlaylistStatCache() {
		return playlistStatCache;
	}

	public void setPlaylistStatCache(PlaylistStatCache playlistStatCache) {
		this.playlistStatCache = playlistStatCache;
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
	
}
