package com.buccitunes.jsonmodel;

import com.buccitunes.model.User;

public class UserPageInfo {
	public User user;
	public boolean isAFollower;
	
	public UserPageInfo(User user, boolean isAFollower) {
		this.user = user;
		this.isAFollower = isAFollower;
	}
	
	
	
}
