package com.buccitunes.model;

import org.springframework.beans.factory.annotation.Value;

import com.buccitunes.constants.CreditCompany;

public class BillingInfo {
	private String cardHolderName;
	private String creditCardNo;
	private String cvv;
	private String billingAddress;
	private CreditCompany creditCardCompany;
	
	//@Value(true)
	private boolean active;
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
}
