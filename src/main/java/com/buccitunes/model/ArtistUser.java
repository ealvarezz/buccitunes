package com.buccitunes.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.buccitunes.constants.Tier;
import com.buccitunes.constants.UserRole;

@Entity(name="ArtistUser")
public class ArtistUser extends User {
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date createDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
	private Artist artist;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_id")
	private BillingInfo billingInfo;
	
	@OneToMany(mappedBy="artistUser")
	private List<ArtistTransaction> paymentHistory;

	private Tier tier;
	
	public ArtistUser(){
		super();
		this.createDate = new Date();
		super.setRole(UserRole.ARTIST);
	}
	
	public ArtistUser(String email, String name, String password, String username) {
		super(email, name, password, username);
		this.createDate = new Date();
		super.setRole(UserRole.ARTIST);
	}
	
	public ArtistUser(User user, Artist artist) {
		super(user.getEmail(), user.getName(), user.getPassword(), user.getUsername());
		this.createDate = new Date();
		this.artist = artist;
		super.setRole(UserRole.ARTIST);
	}
	
	/*
	public ArtistUser(RequestedArtist requestedArtist) {
		createDate = new Date(); 
		super.setRole(UserRole.ARTIST);
	}
	*/

	
	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	
	public void requestAddSong(Song song, Album album) {
		
	}
	
	public void requestAddAlbum(Album album, List<Song> songs) {
		
	}
	
	public void changeBiography(String bio) {
		
	}
	
	public List<Song> displayTopSongs() {
		List<Song> topSongs = new ArrayList<Song>();
		
		return topSongs;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BillingInfo getBillingInfo() {
		return billingInfo;
	}

	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}

	public List<ArtistTransaction> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(List<ArtistTransaction> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}
	
	/*
	public void displayAlbums() {
		
	}
	*/
	
}
