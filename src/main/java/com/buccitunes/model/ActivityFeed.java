package com.buccitunes.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="ACTIVITY_FEED")
public class ActivityFeed {
	
	@EmbeddedId
	private ActivityFeedId id;
	
	public ActivityFeed() {}

	public ActivityFeedId getId() {
		return id;
	}

	public void setId(ActivityFeedId id) {
		this.id = id;
	}
	
	
}
