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
	
	private double balanceRemaining;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="premiumUser")
	private PremiumUser premiumUser;

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

	public double getBalanceRemaining() {
		return balanceRemaining;
	}

	public void setBalanceRemaining(double balanceRemaining) {
		this.balanceRemaining = balanceRemaining;
	}

	public User getPremiumUser() {
		return premiumUser;
	}

	public void setPremiumUser(User premiumUser) {
		premiumUser = premiumUser;
	}
}
