package com.buccitunes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="PremiumUser")
public class PremiumUser extends User {
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_id")
	private BillingInfo billingInfo;
	
	@OneToMany(mappedBy="premiumUser")
	private List<Payment> paymentHistory;
	
	public PremiumUser(){};
	
	public PremiumUser(BillingInfo billingInfo){};
	
	public PremiumUser(User user, BillingInfo billingInfo) {
		super(user.email, user.name, user.password, user.username);
		this.billingInfo = billingInfo;
	}
	
	public BillingInfo getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	public List<Payment> getPaymentHistory() {
		return paymentHistory;
	}
	public void setPaymentHistory(List<Payment> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
}
