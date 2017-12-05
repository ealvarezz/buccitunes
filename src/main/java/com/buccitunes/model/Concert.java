package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="CONCERT")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Concert {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	
	@ManyToOne
    @JoinColumn(name = "artist_id")
	@JsonIgnoreProperties(value = "organizedConcerts")
	Artist mainStar;
	
	@ManyToOne
    @JoinColumn(name = "location_id")
	private Location location;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "Artists_Concerts",
		joinColumns = @JoinColumn(name = "concert_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
	@JsonIgnoreProperties(value = "upcomingConcerts")
	private List<Artist> featuredArtists;
	
	private double price;
	
	private String purchaseLink;
	
	public Concert() {
		
	}
	
	public Concert(RequestedConcert requested) {
		this.name = requested.getName();
		this.releaseDate = requested.getReleaseDate();
		this.location = requested.getLocation();
		this.featuredArtists = requested.getFeaturedArtists();
		this.price = requested.getPrice();
		this.purchaseLink = requested.getPurchaseLink();
		this.mainStar = requested.getRequester().getArtist();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Artist getMainStar() {
		return mainStar;
	}

	public void setMainStar(Artist mainStar) {
		this.mainStar = mainStar;
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
	
}
