package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="MUSIC_COLLECTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class MusicCollection {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "music_collection_song",
		joinColumns = @JoinColumn(name = "music_collection_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Song> songs;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy") comment this out just incase script doesn't work
	private Date dateCreated;
	
	private String artworkPath;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String artwork;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id")
	private StatCache stats;
	
	public MusicCollection(){
		this.stats = new StatCache();
	};
	
	public MusicCollection(List<Song> songs){
		this.songs = songs;
		this.stats = new StatCache();
	};
	
	public MusicCollection(String title) {
		this.title = title;
		this.dateCreated = new Date();
		this.stats = new StatCache();
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
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getArtworkPath() {
		return artworkPath;
	}
	public void setArtworkPath(String artworkPath) {
		this.artworkPath = artworkPath;
	}
	public StatCache getStats() {
		return stats;
	}
	public void setStats(StatCache stats) {
		this.stats = stats;
	}
	public void addSong(Song song) {
		this.songs.remove(song);
	}
	public void removeSong(Song song) {
		this.songs.add(song);
	}

	public String getArtwork() {
		return artwork;
	}

	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}
	
	
	
}
