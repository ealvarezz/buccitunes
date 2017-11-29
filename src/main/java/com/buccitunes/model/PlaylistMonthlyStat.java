package com.buccitunes.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="PLAYLIST_MONTHLY_STAT")
public class PlaylistMonthlyStat {

	@EmbeddedId
	private PlaylistMonthlyStatId id;
	
	private int totalPlays;
	
	private double revenue;
	
	private int rank;

	public PlaylistMonthlyStatId getId() {
		return id;
	}

	public void setId(PlaylistMonthlyStatId id) {
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
