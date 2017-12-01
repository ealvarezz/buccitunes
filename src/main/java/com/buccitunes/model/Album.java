package com.buccitunes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;

import org.springframework.format.annotation.DateTimeFormat;

import com.buccitunes.resultset.AlbumWithStats;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity(name="Album")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Album extends MusicCollection {
	
	
	@ManyToOne
    @JoinColumn(name = "primary_artist_id")
	@JsonIgnoreProperties(value = "albums")
	//@JsonBackReference
	private Artist primaryArtist;
	

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "artists_featured_on_album",
		joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Artist> featuredArtists;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "album_id")
	@JsonIgnoreProperties(value = "album")
	private List<Song> songs;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	
	private String label;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "genre_album",
		joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Genre> genres;
	
	@OneToOne(mappedBy="album", cascade=CascadeType.ALL)
	@JsonIgnoreProperties(value = "album")
	private AlbumStatCache albumStats;
	
	private boolean isPublic;
	
	
	public Album(){
		super();
	};
	
	public AlbumStatCache getAlbumStats() {
		return albumStats;
	}

	public void setAlbumStats(AlbumStatCache albumStats) {
		this.albumStats = albumStats;
	}

	public Album(List<Song> newSongs){
		this.songs = newSongs;
	};
	
	public Album(RequestedAlbum requested, boolean addSongs) {
		
		super(requested.getTitle());
		this.primaryArtist = requested.getPrimaryArtist();
		this.featuredArtists = requested.getFeaturedArtists();
		this.releaseDate = requested.getReleaseDate();
		this.label = requested.getLabel();
		this.genres = requested.getGenres();
		
		List<Song> newSongs = new ArrayList<Song>();
		
		if(addSongs) {
			List<RequestedSong> requestedSongs = requested.getSongs();
			
			for(RequestedSong requestedSong : requestedSongs) {
				Song newSong = new Song(requestedSong);
				
				if(newSong.getOwner() == null) {
					newSong.setOwner(requested.getPrimaryArtist());
				}
				
				newSongs.add(newSong);
			}
			
			this.setSongs(newSongs);
		}
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public Artist getPrimaryArtist() {
		return primaryArtist;
	}
	public void setPrimaryArtist(Artist primaryArtist) {
		this.primaryArtist = primaryArtist;
	}
	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}
	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}
