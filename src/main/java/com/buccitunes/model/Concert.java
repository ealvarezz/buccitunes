package com.buccitunes.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Concert {
	private int id;
	private String name;
	private Location location;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date releaseDate;
	private List<Artist> featuredArtists;
	private double price;
	private String purchaseLink;
	
	
	public Concert(RequestedConcert requested) {
		
	}
	
	
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
	
}
