package com.buccitunes.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name="BillingInfo")
public class BillingInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String cardHolderName;
	
	private String creditCardNo;
	
	private String cvv;
	
	private String billingAddress;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date nextBillingDate;
	
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
	
	public Date getNextBillingDate() {
		return nextBillingDate;
	}
	public void setNextBillingDate(Date nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
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
		if(!validCreditCardNumber()){
			return "Invalid credit card number";
		}
		
		return "";
	}
	
	/**
	 * Checks if the credit card is valid using The Luhn Algorithm
	 * @return if the credit card is valid or not
	 */
	private boolean validCreditCardNumber()
	{
	   
	   int sum = 0;
	   /*boolean alternate = false;
	   for (int i = creditCardNo.length() - 1; i >= 0; i--)
	   {
	       int n = Integer.parseInt(creditCardNo.substring(i, i + 1));
	       if (alternate)
	       {
	           n *= 2;
	           if (n > 9)
	           {
	                n = (n % 10) + 1;
	            }
	        }
	        sum += n;
	        alternate = !alternate;
	    }*/
	    return (sum % 10 == 0);
	 }
	
	public void updateBillingInfo(BillingInfo billing) {
		if(billing.billingAddress != null) {
			this.billingAddress = billing.billingAddress;
		}
		
		if(billing.creditCardNo != null) {
			this.creditCardNo = billing.creditCardNo;
		}
		
		if(billing.creditCardCompany != null) {
			this.creditCardCompany = billing.creditCardCompany;
		}
		
		if(billing.cvv != null) {
			this.cvv = billing.cvv;
		}
		
		if(billing.cardHolderName != null) {
			this.cardHolderName = billing.cardHolderName;
		}
	}
}
