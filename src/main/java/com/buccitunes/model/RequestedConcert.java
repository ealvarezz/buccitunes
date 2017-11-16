package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="RequestedConcert")
public class RequestedConcert {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@ManyToOne
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
	private Location location;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Artists_Concerts_Requested",
		joinColumns = @JoinColumn(name = "requested_concert_id", referencedColumnName = "id", insertable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", insertable = false, updatable = false))
	private List<Artist> featuredArtists;
	
	private double price;
	
	private String purchaseLink;
	
	private String comments;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requested_artist_id", insertable = false, updatable = false)
	private ArtistUser requester;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<Artist> getFeaturedArtists() {
		return featuredArtists;
	}
	public void setFeaturedArtists(List<Artist> featuredArtists) {
		this.featuredArtists = featuredArtists;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPurchaseLink() {
		return purchaseLink;
	}
	public void setPurchaseLink(String purchaseLink) {
		this.purchaseLink = purchaseLink;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public ArtistUser getRequester() {
		return requester;
	}
	public void setRequester(ArtistUser requester) {
		this.requester = requester;
	}
}
