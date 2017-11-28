package com.buccitunes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class AlbumMonthlyStatId implements Serializable{

	@OneToOne
	@JoinColumn(name = "album_id")
	private Album album;
	
	private Date month;
	
	public AlbumMonthlyStatId() {}
	
	public AlbumMonthlyStatId(Album album, Date month) {
		
		this.album = album;
		this.month = month;
	}

	public Album getAlbum() {
		return album;
	}

	public Date getMonth() {
		return month;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlbumMonthlyStatId)) return false;
        AlbumMonthlyStatId that = (AlbumMonthlyStatId) o;
        return Objects.equals(getAlbum(), that.getAlbum()) &&
                Objects.equals(getMonth(), that.getMonth());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getAlbum(), getMonth());
    }
	
}
