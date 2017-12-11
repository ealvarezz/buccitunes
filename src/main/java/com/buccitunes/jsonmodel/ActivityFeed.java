package com.buccitunes.jsonmodel;

import java.util.Date;

public class ActivityFeed {
	
	private String feed;
	private Date date;
	
	public ActivityFeed() {}

	public ActivityFeed(String feed, Date date) {
		super();
		this.feed = feed;
		this.date = date;
	}

	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
