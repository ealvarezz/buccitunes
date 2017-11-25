package com.buccitunes.miscellaneous;

public class BucciConstants {
	public static class PageRequest {
		public static final int START = 0;
	}
	
	public static class Album {
		public static final int NEW_RELASES_LIMIT = 10;
		public static final int TOP_ALBUMS_LIMIT = 10;
	}
	
	public static class Artist {
		public static final int TOP_SONGS_LIMIT = 10;
	}
	
	public static class Admin {
		public static final double ROYALTY_PRICE = 0.03;
		public static final int MOONMAN_TIER_MONTHLY_PRICE = 30;
		public static final int TREX_TIER_MONTHLY_PRICE = 45;
		public static final int NITRODUBS_TIER_MONTHLY_PRICE = 100;
		public static final int MOONMAN_MAX = 4;
		public static final int TREX_MAX = 9;
		public static final int NITRODUBS_MAX = 17;
		
	}
	
	public static class Playlist {
		public static final int TOP_PLAYLISTS_LIMIT = 10;
	}
	
	public static class TimeAgo {
		public static final String WEEK_AGO = "7";
		public static final String TWO_WEEKS_AGO = "14";
		public static final String ALL_TIME = "CURDATE()";
	}
	
	public static class Stats {
		public static final String SONG_COUNT = "stats.song_count";
		public static final String PLAY_COUNT = "numPlays";
	}
}
