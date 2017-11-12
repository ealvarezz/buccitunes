package com.buccitunes.miscellaneous;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
	private final static String FILESDIR = "/BucciTunesFiles/";
	private final static Path FILESPATH = Paths.get(System.getProperty("user.home") + FILESDIR);
	private final static Path USERPATH = Paths.get(FILESPATH + "USERS/");
	private final static Path ARTISTPATH = Paths.get(FILESPATH + "ARTISTS/");
	private final static Path ALBUMPATH = Paths.get(FILESPATH + "ALBUMS/");
	private final static Path PLAYLISTPATH = Paths.get(FILESPATH + "PLAYLISTS/");
	private final static Path SONGPATH = Paths.get(FILESPATH + "SONGS/");

	
	public static void setUpFileDirectory() throws IOException {
		if(Files.notExists(FILESPATH)) {
			Files.createDirectory(FILESPATH);
			Files.createDirectory(USERPATH);
			Files.createDirectory(ARTISTPATH);
			Files.createDirectory(ALBUMPATH);
			Files.createDirectory(PLAYLISTPATH);
			Files.createDirectory(SONGPATH);
		}
	}
	
	
	public static String getEncodedArtistArtwork(int id) {
		
		return "";
	}
}
