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

import com.buccitunes.jsonmodel.CurrentStats;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="MUSIC_COLLECTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class MusicCollection {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date dateCreated;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String artwork;
	
	private String artworkPath;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CurrentStats currentStats;
	
	
	public MusicCollection(){
		this.dateCreated = new Date();
	};
	
	public MusicCollection(String title) {
		this.title = title;
		this.dateCreated = new Date();
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
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	

	public String getArtwork() {
		return artwork;
	}

	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}

	public String getArtworkPath() {
		return artworkPath;
	}

	public void setArtworkPath(String artworkPath) {
		this.artworkPath = artworkPath;
	}

	public CurrentStats getCurrentStats() {
		return currentStats;
	}

	public void setCurrentStats(CurrentStats currentStats) {
		this.currentStats = currentStats;
	}
}
