package com.buccitunes.miscellaneous;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.buccitunes.model.Artist;

public class FileManager {
	private final static String FILESDIR = "/BucciTunesFiles/";
	private final static Path FILESPATH = Paths.get(System.getProperty("user.home") + FILESDIR);
	private final static Path USERPATH = Paths.get(FILESPATH + "USERS/");
	private final static Path ARTISTPATH = Paths.get(FILESPATH + "ARTISTS/");
	private final static Path ALBUMPATH = Paths.get(FILESPATH + "ALBUMS/");
	private final static Path PLAYLISTPATH = Paths.get(FILESPATH + "PLAYLISTS/");
	private final static Path SONGPATH = Paths.get(FILESPATH + "SONGS/");
	private final static String ARTWORKIMAGE = "artwork.png";

	
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
	
	public static void setArtistDirectory(Artist artist) throws IOException {
		
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path artistDir = Paths.get(ALBUMPATH + "/" + artist.getId() + "/");
		if(Files.notExists(artistDir)) {
			Files.createDirectory(artistDir);
		}
		
		Path artworkImage = Paths.get(artistDir + ARTWORKIMAGE);
		if(Files.notExists(artworkImage)) {
			Files.createDirectory(artworkImage);
		}
		
		
	}
	
	public static String saveArtwork(String encodedStr, Artist artist) throws IOException {
		
		 byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
         Path path = Paths.get(ALBUMPATH + "/" + artist.getId() +  "/" + ARTWORKIMAGE);
         
         System.out.println(path);
         
         if(Files.notExists(path)) {
        	 setArtistDirectory(artist);
         }

         try{
             Files.write(path,decodedBytes);
             System.out.println("Success");
         }
         catch (Exception ex) {
             ex.printStackTrace();
         }
         
         return path.toString();
	}
	
	public static String getEncodedArtistArtwork(int id) {
		
		return "";
	}
}
