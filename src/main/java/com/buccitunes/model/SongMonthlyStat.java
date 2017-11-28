package com.buccitunes.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="SONG_MONTHLY_STAT")
public class SongMonthlyStat {

	@EmbeddedId
	private SongMonthlyStatId id;
	
	private int totalPlays;
	
	private double revenue;
	
	private int rank;

	public SongMonthlyStatId getId() {
		return id;
	}

	public void setId(SongMonthlyStatId id) {
		this.id = id;
	}

	public int getTotalPlays() {
		return totalPlays;
	}

	public void setTotalPlays(int totalPlays) {
		this.totalPlays = totalPlays;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
