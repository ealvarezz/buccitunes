package com.buccitunes.model;

import java.util.List;

public class PremiumUser extends User {
	private BillingInfo billingInfo;
	private List<Payment> paymentHistory;
	
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
