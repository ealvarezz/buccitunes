package com.buccitunes.model;

public class RequestedArtist {
	private int id;
	
	private String biography;
	
	private String comments;
	
	private User requester;

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
	
	//private requestedRoyaltyTier 
	
	
}
