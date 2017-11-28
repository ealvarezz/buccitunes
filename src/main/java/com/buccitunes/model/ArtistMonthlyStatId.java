package com.buccitunes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class ArtistMonthlyStatId implements Serializable{

	@OneToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	private Date month;
	
	public ArtistMonthlyStatId() {}
	
	public ArtistMonthlyStatId(Artist album, Date month) {
		
		this.artist = album;
		this.month = month;
	}

	public Artist getArtist() {
		return artist;
	}

	public Date getMonth() {
		return month;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistMonthlyStatId)) return false;
        ArtistMonthlyStatId that = (ArtistMonthlyStatId) o;
        return Objects.equals(getArtist(), that.getArtist()) &&
                Objects.equals(getMonth(), that.getMonth());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getArtist(), getMonth());
    }
}
