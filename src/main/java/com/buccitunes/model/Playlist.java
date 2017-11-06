package com.buccitunes.model;

import java.util.List;

public class Playlist extends MusicCollection {
	private boolean isCollaborative;
	private boolean isPublic;
	private User owner;
	private List<User> collaboratives;
	private List<User> invitedViewers;
	
	
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
	public List<User> getInvitedViewers() {
		return invitedViewers;
	}
	public void setInvitedViewers(List<User> invitedViewers) {
		this.invitedViewers = invitedViewers;
	}
	public void canEdit(User user) {
		
	}
	public void canView(User user) {
		
	}
	
}
