package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity(name="REQUESTED_ARTIST")
public class RequestedArtist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String biography;
	
	private String comments;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User requester;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBiography() {
		return biography;
	}
	
	public void setBiography(String biography) {
		this.biography = biography;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public User getRequester() {
		return requester;
	}
	
	public void setRequester(User requester) {
		this.requester = requester;
	}
	
	
	
	
}
