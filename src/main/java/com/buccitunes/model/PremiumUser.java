package com.buccitunes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.buccitunes.constants.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="PremiumUser")
public class PremiumUser extends User {
	
	private Date joinDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_id")
	private BillingInfo billingInfo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "premium_user_id")
	@JsonIgnoreProperties(value = "premiumUser")
	private List<Payment> paymentHistory;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Date nextBillingDate;
	
	public PremiumUser(){
		super();
		this.joinDate = new Date();
	};
	
	public PremiumUser(BillingInfo billingInfo){
		super();
		this.joinDate = new Date();
		super.setRole(UserRole.PREMIUM);
	};
	
	public PremiumUser(User user, BillingInfo billingInfo) {
		super(user.email, user.name, user.password, user.username);
		this.billingInfo = billingInfo;
		this.joinDate = new Date();
		super.setRole(UserRole.PREMIUM);
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

	public Date getJoinDate() {
		return joinDate;
	}
	
	public boolean makePayment(Double amount) {
		boolean paid = true;
		if(paymentHistory == null) {
			paymentHistory = new ArrayList<Payment>();
		}
		Payment payment = new Payment(amount, true, this);
		
		paymentHistory.add(payment);
		return paid;
	}

	public Date getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(Date nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}
}
