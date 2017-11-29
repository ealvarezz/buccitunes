package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="PLAYLIST_STAT_CACHE")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "playlist")
public class PlaylistStatCache extends StatCache{
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="playlist_playlist_stat_cache", 
	joinColumns = @JoinColumn(name="playlist_stat_cache_id", referencedColumnName = "id", insertable = false, updatable = false),
	inverseJoinColumns = @JoinColumn(name="playlist_id", referencedColumnName = "id", insertable = false, updatable = false))
	@JsonIgnoreProperties(value = "playlistStatCache")
	private Playlist playlist;
	
	public PlaylistStatCache() {}
	
	public PlaylistStatCache(int totalPlays, int monthlyPlays, double totalRevenue, double monthlyRevenue, int rank, Playlist playlist) {
		super(totalPlays, monthlyPlays, totalRevenue, monthlyRevenue, rank);
		this.playlist = playlist;
	}

	public Playlist getPlaylist() {
		return playlist;
	}
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	
}
