package com.buccitunes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

@Entity(name="BillingInfo")
public class BillingInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String cardHolderName;
	
	private String creditCardNo;
	
	private String cvv;
	
	private String billingAddress;
	
	@ManyToOne
    @JoinColumn(name = "credit_company_id")
	private CreditCompany creditCardCompany;
	
	private boolean active = true;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public CreditCompany getCreditCardCompany() {
		return creditCardCompany;
	}
	public void setCreditCardCompany(CreditCompany creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String checkInvalidInfo() {
		return "";
	}
}
