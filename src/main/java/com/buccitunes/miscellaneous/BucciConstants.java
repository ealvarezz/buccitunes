package com.buccitunes.miscellaneous;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="constants")
public class BucciConstants {

	private int start;
	
	private int newReleasesLimit;	
	private int topAlbumsLimit;
	private int topSongsLimit;
	private int topPlaylistsLimit;

	private double royaltyPrice;
	
	private int moonmanTierMonthlyPrice;
	private int trexTierMonthlyPrice;
	private int nitrodubsTierMonthlyPrice;
	private int moonmanMax;
	private int trexMax;
	private int nitrodubsMax;
	
	private String weekAgo;
	private String twoWeeksAgo;
	private String allTime;
	
	private String songCount;
	private String playCount;
	
	private String notLoggedIn;
	private String loggedIn;
	private String session;
	
	private double signupPremiumPrice;
	private double monthlyPremiumPrice;
	
	
	public int getStart() {
		return start;
	}
	
	public int getNewReleasesLimit() {
		return newReleasesLimit;
	}
	
	public int getTopAlbumsLimit() {
		return topAlbumsLimit;
	}
	
	public int getTopSongsLimit() {
		return topSongsLimit;
	}
	
	public int getTopPlaylistsLimit() {
		return topPlaylistsLimit;
	}
	
	public double getRoyaltyPrice() {
		return royaltyPrice;
	}
	
	public int getMoonmanTierMonthlyPrice() {
		return moonmanTierMonthlyPrice;
	}
	
	public int getTrexTierMonthlyPrice() {
		return trexTierMonthlyPrice;
	}
	
	public int getNitrodubsTierMonthlyPrice() {
		return nitrodubsTierMonthlyPrice;
	}
	
	public int getMoonmanMax() {
		return moonmanMax;
	}
	
	public int getTrexMax() {
		return trexMax;
	}
	
	public int getNitrodubsMax() {
		return nitrodubsMax;
	}
	
	public String getWeekAgo() {
		return weekAgo;
	}
	
	public String getTwoWeeksAgo() {
		return twoWeeksAgo;
	}
	
	public String getAllTime() {
		return allTime;
	}
	
	public String getSongCount() {
		return songCount;
	}
	
	public String getPlayCount() {
		return playCount;
	}
	
	public String getNotLoggedIn() {
		return notLoggedIn;
	}
	
	public String getLoggedIn() {
		return loggedIn;
	}
	
	public String getSession() {
		return session;
	}

	public double getSignupPremiumPrice() {
		return signupPremiumPrice;
	}

	public double getMonthlyPremiumPrice() {
		return monthlyPremiumPrice;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setNewReleasesLimit(int newReleasesLimit) {
		this.newReleasesLimit = newReleasesLimit;
	}

	public void setTopAlbumsLimit(int topAlbumsLimit) {
		this.topAlbumsLimit = topAlbumsLimit;
	}

	public void setTopSongsLimit(int topSongsLimit) {
		this.topSongsLimit = topSongsLimit;
	}

	public void setTopPlaylistsLimit(int topPlaylistsLimit) {
		this.topPlaylistsLimit = topPlaylistsLimit;
	}

	public void setRoyaltyPrice(double royaltyPrice) {
		this.royaltyPrice = royaltyPrice;
	}

	public void setMoonmanTierMonthlyPrice(int moonmanTierMonthlyPrice) {
		this.moonmanTierMonthlyPrice = moonmanTierMonthlyPrice;
	}

	public void setTrexTierMonthlyPrice(int trexTierMonthlyPrice) {
		this.trexTierMonthlyPrice = trexTierMonthlyPrice;
	}

	public void setNitrodubsTierMonthlyPrice(int nitrodubsTierMonthlyPrice) {
		this.nitrodubsTierMonthlyPrice = nitrodubsTierMonthlyPrice;
	}

	public void setMoonmanMax(int moonmanMax) {
		this.moonmanMax = moonmanMax;
	}

	public void setTrexMax(int trexMax) {
		this.trexMax = trexMax;
	}

	public void setNitrodubsMax(int nitrodubsMax) {
		this.nitrodubsMax = nitrodubsMax;
	}

	public void setWeekAgo(String weekAgo) {
		this.weekAgo = weekAgo;
	}

	public void setTwoWeeksAgo(String twoWeeksAgo) {
		this.twoWeeksAgo = twoWeeksAgo;
	}

	public void setAllTime(String allTime) {
		this.allTime = allTime;
	}

	public void setSongCount(String songCount) {
		this.songCount = songCount;
	}

	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}

	public void setNotLoggedIn(String notLoggedIn) {
		this.notLoggedIn = notLoggedIn;
	}

	public void setLoggedIn(String loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void setSignupPremiumPrice(double signupPremiumPrice) {
		this.signupPremiumPrice = signupPremiumPrice;
	}

	public void setMonthlyPremiumPrice(double monthlyPremiumPrice) {
		this.monthlyPremiumPrice = monthlyPremiumPrice;
	}
}