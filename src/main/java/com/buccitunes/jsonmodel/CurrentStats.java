package com.buccitunes.jsonmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CurrentStats {
	
	private int numPlays;
	
	public CurrentStats(int numPlays) {
		this.numPlays = numPlays;
	}

	public int getNumPlays() {
		return numPlays;
	}

	public void setNumPlays(int numPlays) {
		this.numPlays = numPlays;
	}
}
