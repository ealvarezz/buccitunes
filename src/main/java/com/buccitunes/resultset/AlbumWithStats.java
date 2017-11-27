package com.buccitunes.resultset;

import com.buccitunes.model.Album;

public class AlbumWithStats extends Album{
	private int numPlays;
	
	public AlbumWithStats(){
		super();
	}
	
	public AlbumWithStats(Album album , int numPlays) {
		super();
		this.numPlays = numPlays;
	}

	public int getNumPlays() {
		return numPlays;
	}

	public void setNumPlays(int numPlays) {
		this.numPlays = numPlays;
	}

}
