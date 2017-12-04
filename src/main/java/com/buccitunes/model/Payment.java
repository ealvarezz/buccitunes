package com.buccitunes.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="Payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private boolean isPaid;
	
	private boolean isPending;
	
	private double amount;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date date;
	
	@ManyToOne(cascade = {CascadeType.MERGE , CascadeType.PERSIST})
    @JoinColumn(name = "premium_user_id")
	@JsonIgnoreProperties(value = "paymentHistory")
	private PremiumUser premiumUser;
	
	public Payment(){
		this.date = new Date();
	}

	public Payment(double amount, boolean isPaid, PremiumUser premiumUser) {
		this.amount = amount;
		this.isPaid = isPaid;
		this.isPending = false;
		this.premiumUser = premiumUser;
		this.date = new Date();
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

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

	public User getPremiumUser() {
		return premiumUser;
	}

	public void setPremiumUser(PremiumUser premiumUser) {
		this.premiumUser = premiumUser;
	}
}
