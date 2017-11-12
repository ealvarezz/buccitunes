package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Lyrics")
public class Lyrics {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String lyric;

	public Lyrics() {}
	
	public Lyrics(String lyric) {
		this.lyric = lyric;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	
	
	
}
