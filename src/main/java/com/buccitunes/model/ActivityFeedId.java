package com.buccitunes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class ActivityFeedId implements Serializable{
	
	private String feed;
	private Date date;
	
	private ActivityFeedId() {}

	public ActivityFeedId(String feed, Date date) {
		super();
		this.feed = feed;
		this.date = date;
	}

	public String getFeed() {
		return feed;
	}

	public Date getDate() {
		return date;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityFeedId)) return false;
        ActivityFeedId that = (ActivityFeedId) o;
        return Objects.equals(getFeed(), that.getFeed()) &&
                Objects.equals(getDate(), that.getDate());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getFeed(), getDate());
    }
	
}
