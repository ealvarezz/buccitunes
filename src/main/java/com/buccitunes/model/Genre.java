package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="GENRE")
public class Genre {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Id
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	HIPHOP, COUNTRY, POP, LATIN, SLAV, BLOCK, RNB, METAL, PROGRESSIVE_METAL, FUNK, ROCK, ELECTRONIC, JAZZ, PUNK, INDIE, RUSSIAN_OPERA, OPERA
	, WEEB, UK_RAP
	*/
}
