package com.buccitunes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class SongMonthlyStatId implements Serializable{

	@OneToOne
	@JoinColumn(name = "song_id")
	private Song song;
	
	private Date month;
	
	public SongMonthlyStatId() {}
	
	public SongMonthlyStatId(Song song, Date month) {
		
		this.song = song;
		this.month = month;
	}

	public Song getSong() {
		return song;
	}

	public Date getMonth() {
		return month;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongMonthlyStatId)) return false;
        SongMonthlyStatId that = (SongMonthlyStatId) o;
        return Objects.equals(getSong(), that.getSong()) &&
                Objects.equals(getMonth(), that.getMonth());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getSong(), getMonth());
    }
}
