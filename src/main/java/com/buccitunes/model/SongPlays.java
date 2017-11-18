package com.buccitunes.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="SongPlays")
public class SongPlays {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	User user;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
	Song song;
	
	Date datePlayed;
	
	public SongPlays(User user, Song song) {
		
		this.user = user;
		this.song = song;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Song getSong() {
		return song;
	}
	
	public void setSong(Song song) {
		this.song = song;
	}

	public Date getDatePlayed() {
		return datePlayed;
	}

	public void setDatePlayed(Date datePlayed) {
		this.datePlayed = datePlayed;
	}
	
	
}
