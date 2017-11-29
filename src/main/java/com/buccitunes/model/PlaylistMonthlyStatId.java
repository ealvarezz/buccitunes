package com.buccitunes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class PlaylistMonthlyStatId implements Serializable{

	@OneToOne
	@JoinColumn(name = "playlist_id")
	private Playlist playlist;
	
	private Date month;
	
	public PlaylistMonthlyStatId() {}
	
	public PlaylistMonthlyStatId(Playlist playlist, Date month) {
		
		this.playlist = playlist;
		this.month = month;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public Date getMonth() {
		return month;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistMonthlyStatId)) return false;
        PlaylistMonthlyStatId that = (PlaylistMonthlyStatId) o;
        return Objects.equals(getPlaylist(), that.getPlaylist()) &&
                Objects.equals(getMonth(), that.getMonth());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getPlaylist(), getMonth());
    }
}
