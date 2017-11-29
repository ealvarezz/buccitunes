package com.buccitunes.miscellaneous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
public class BucciConstant {

	public static int START;
	
	public static int NEW_RELASES_LIMIT;	
	public static int TOP_ALBUMS_LIMIT;
	
	public static int TOP_SONGS_LIMIT;

	public static double ROYALTY_PRICE;
	
	public static int MOONMAN_TIER_MONTHLY_PRICE;
	public static int TREX_TIER_MONTHLY_PRICE;
	public static int NITRODUBS_TIER_MONTHLY_PRICE;
	public static int MOONMAN_MAX;
	public static int TREX_MAX;
	public static int NITRODUBS_MAX;

	public static int TOP_PLAYLISTS_LIMIT;
	
	public static String WEEK_AGO;
	public static String TWO_WEEKS_AGO;
	public static String ALL_TIME;
	
	
	public static String SONG_COUNT;
	public static String PLAY_COUNT;
	
	public static String NOT_LOGGED_IN;
	public static String LOGGED_IN;
	public static String SESSION;
	
	
	@Value("${constants.START}")
	private void setSTART(int sTART) {
		START = sTART;
	}
	@Value("${constants.NEW_RELEASES_LIMIT}")
	private void setNEW_RELASES_LIMIT(int nEW_RELASES_LIMIT) {
		NEW_RELASES_LIMIT = nEW_RELASES_LIMIT;
	}
	@Value("${constants.TOP_ALBUMS_LIMIT}")
	private void setTOP_ALBUMS_LIMIT(int tOP_ALBUMS_LIMIT) {
		TOP_ALBUMS_LIMIT = tOP_ALBUMS_LIMIT;
	}
	@Value("${constants.TOP_SONGS_LIMIT}")
	private void setTOP_SONGS_LIMIT(int tOP_SONGS_LIMIT) {
		TOP_SONGS_LIMIT = tOP_SONGS_LIMIT;
	}
	@Value("${constants.ROYALTY_PRICE}")
	private void setROYALTY_PRICE(double rOYALTY_PRICE) {
		ROYALTY_PRICE = rOYALTY_PRICE;
	}
	@Value("${constants._TIER_MONTHLY_PRICE}")
	private void setMOONMAN_TIER_MONTHLY_PRICE(int mOONMAN_TIER_MONTHLY_PRICE) {
		MOONMAN_TIER_MONTHLY_PRICE = mOONMAN_TIER_MONTHLY_PRICE;
	}
	@Value("${constants.TREX_TIER_MONTHLY_PRICE}")
	private void setTREX_TIER_MONTHLY_PRICE(int tREX_TIER_MONTHLY_PRICE) {
		TREX_TIER_MONTHLY_PRICE = tREX_TIER_MONTHLY_PRICE;
	}
	@Value("${constants.NITRODUBS_TIER_MONTHLY_PRICE}")
	private void setNITRODUBS_TIER_MONTHLY_PRICE(int nITRODUBS_TIER_MONTHLY_PRICE) {
		NITRODUBS_TIER_MONTHLY_PRICE = nITRODUBS_TIER_MONTHLY_PRICE;
	}
	@Value("${constants.MOONMAN_MAX}")
	private void setMOONMAN_MAX(int mOONMAN_MAX) {
		MOONMAN_MAX = mOONMAN_MAX;
	}
	@Value("${constants.TREX_MAX}")
	private void setTREX_MAX(int tREX_MAX) {
		TREX_MAX = tREX_MAX;
	}
	@Value("${constants.NITRODUBS_MAX}")
	private void setNITRODUBS_MAX(int nITRODUBS_MAX) {
		NITRODUBS_MAX = nITRODUBS_MAX;
	}
	@Value("${constants.TOP_PLAYLISTS_LIMIT}")
	private void setTOP_PLAYLISTS_LIMIT(int tOP_PLAYLISTS_LIMIT) {
		TOP_PLAYLISTS_LIMIT = tOP_PLAYLISTS_LIMIT;
	}
	@Value("${constants.WEEK_AGO}")
	private void setWEEK_AGO(String wEEK_AGO) {
		WEEK_AGO = wEEK_AGO;
	}
	@Value("${constants.TWO_WEEKS_AGO}")
	private void setTWO_WEEKS_AGO(String tWO_WEEKS_AGO) {
		TWO_WEEKS_AGO = tWO_WEEKS_AGO;
	}
	@Value("${constants.ALL_TIME}")
	private void setALL_TIME(String aLL_TIME) {
		ALL_TIME = aLL_TIME;
	}
	@Value("${constants.SONG_COUNT}")
	private void setSONG_COUNT(String sONG_COUNT) {
		SONG_COUNT = sONG_COUNT;
	}
	@Value("${constants.PLAY_COUNT}")
	private void setPLAY_COUNT(String pLAY_COUNT) {
		PLAY_COUNT = pLAY_COUNT;
	}
	@Value("${constants.NOT_LOGGED_IN}")
	private void setNOT_LOGGED_IN(String nOT_LOGGED_IN) {
		NOT_LOGGED_IN = nOT_LOGGED_IN;
	}
	@Value("${constants.LOGGED_IN}")
	private void setLOGGED_IN(String lOGGED_IN) {
		LOGGED_IN = lOGGED_IN;
	}
	@Value("${constants.SESSION}")
	private void setSESSION(String sESSION) {
		SESSION = sESSION;
	}
	
	
	
}
