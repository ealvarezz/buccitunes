package com.buccitunes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="STAT_CACHE")
@Inheritance(strategy = InheritanceType.JOINED)
public class StatCache {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int totalPlays;
	private int monthlyPlays;
	private double totalRevenue;
	private double monthlyRevenue;
	private int rank;
	
	public StatCache() {}
	
	public StatCache(int totalPlays, int monthlyPlays, double totalRevenue, double monthlyRevenue, int rank) {
		super();
		this.totalPlays = totalPlays;
		this.monthlyPlays = monthlyPlays;
		this.totalRevenue = totalRevenue;
		this.monthlyRevenue = monthlyRevenue;
		this.rank = rank;
	}

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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
	
}
