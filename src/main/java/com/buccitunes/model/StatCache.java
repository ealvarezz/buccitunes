package com.buccitunes.model;

public class StatCache {
	private int totalPlays;
	private int monthlyPlays;
	private double totalRevenue;
	private double monthlyRevenue;
	private double popularity;
	private double monthlyStats;
	
	public int getTotalPlays() {
		return totalPlays;
	}
	public void setTotalPlays(int totalPlays) {
		this.totalPlays = totalPlays;
	}
	public int getMonthlyPlays() {
		return monthlyPlays;
	}
	public void setMonthlyPlays(int monthlyPlays) {
		this.monthlyPlays = monthlyPlays;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public double getMonthlyRevenue() {
		return monthlyRevenue;
	}
	public void setMonthlyRevenue(double monthlyRevenue) {
		this.monthlyRevenue = monthlyRevenue;
	}
	public double getPopularity() {
		return popularity;
	}
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	public double getMonthlyStats() {
		return monthlyStats;
	}
	public void setMonthlyStats(double monthlyStats) {
		this.monthlyStats = monthlyStats;
	}
	
	
}
