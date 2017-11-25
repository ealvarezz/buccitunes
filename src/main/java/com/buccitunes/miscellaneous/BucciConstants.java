package com.buccitunes.miscellaneous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="constants")
public class BucciConstants {
	public static class PageRequest {
		public static int START;
	}
	
	public static class Album {
		public static int NEW_RELASES_LIMIT;
		public static int TOP_ALBUMS_LIMIT;
	}
	
	public static class Artist {
		public static int TOP_SONGS_LIMIT;
	}
	
	public static class Admin {
		public static double ROYALTY_PRICE;
		public static  int MOONMAN_TIER_MONTHLY_PRICE;
		public static int TREX_TIER_MONTHLY_PRICE;
		public static int NITRODUBS_TIER_MONTHLY_PRICE;
		public static int MOONMAN_MAX;
		public static int TREX_MAX;
		public static int NITRODUBS_MAX;
		
	}
	
	public static class Playlist {
		public static int TOP_PLAYLISTS_LIMIT;
	}
	
	public static class TimeAgo {
		public static String WEEK_AGO;
		public static String TWO_WEEKS_AGO;
		public static String ALL_TIME;
	}
	
	public static class Stats {
		public static String SONG_COUNT;
		public static String PLAY_COUNT;
	}
	
	public static class User {
		public static String NOT_LOGGED_IN;
		public static String LOGGED_IN;
		public static String SESSION;
	}
}
