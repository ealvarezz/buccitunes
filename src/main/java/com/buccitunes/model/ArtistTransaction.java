package com.buccitunes.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.buccitunes.constants.PaymentType;

@Entity(name="ArtistTransaction")
public class ArtistTransaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private double amount;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date date;
	
	private PaymentType paymentType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="artist_id")
	private Artist artist;
	
	public ArtistTransaction(){}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		artist = artist;
	}

	
	
	
}
