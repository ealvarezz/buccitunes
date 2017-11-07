package com.buccitunes.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Payment {
	private boolean isPaid;
	
	private boolean isPending;
	
	private double amount;
	
	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date date;
	
	private double balanceRemaining;
	
	private User PremiumUser;

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
		return PremiumUser;
	}

	public void setPremiumUser(User premiumUser) {
		PremiumUser = premiumUser;
	}
}
