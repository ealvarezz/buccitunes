package com.buccitunes.miscellaneous;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;

public class FileManager {
	private final static String FILESDIR = "/BucciTunesFiles";
	private final static Path FILESPATH = Paths.get(System.getProperty("user.home").toString() + FILESDIR + "/");
	private final static Path USERPATH = Paths.get(FILESPATH.toString() + "/USERS/");
	private final static Path ARTISTPATH = Paths.get(FILESPATH.toString() + "/ARTISTS/");
	private final static Path ALBUMPATH = Paths.get(FILESPATH.toString() + "/ALBUMS/");
	private final static Path PLAYLISTPATH = Paths.get(FILESPATH.toString() + "/PLAYLISTS/");
	private final static Path SONGPATH = Paths.get(FILESPATH.toString() + "/SONGS/");
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
	
	
	public static void setArtistDirectory(int artistId) throws IOException {
		
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path artistDir = Paths.get(ARTISTPATH.toString() + "/" + artistId + "/");
		if(Files.notExists(artistDir)) {
			Files.createDirectory(artistDir);
		}
		
		
		
		
	}
	
public static void setAlbumDirectory(int artistId) throws IOException {
		
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path albumDir = Paths.get(ALBUMPATH.toString() + "/" + artistId + "/");
		if(Files.notExists(albumDir)) {
			Files.createDirectory(albumDir);
		}
		
		
		
		
	}
	
	public static String saveArtwork(String encodedStr, int albumId) throws IOException {
		
		 byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
		 
		 System.out.println("I  MADE IT HERE!!!!!!!!!!!!!!!!!!!!!!!!\n\n\n\n\n");
         Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
         
         System.out.println(path);
         
         if(Files.notExists(path)) {
        	 	setAlbumDirectory(albumId);
         }

         try{
        	 	
             //Files.write(path,decodedBytes);
             
            InputStream in = new ByteArrayInputStream(decodedBytes);
				
			BufferedImage bImageFromConvert = ImageIO.read(in);
					
			ImageIO.write(bImageFromConvert, "png", path.toFile()); // File might be null
				
            System.out.println("Success");
         }
         catch (Exception ex) {
             ex.printStackTrace();
         }
         
         return path.toString();
	}
	
	public static String saveArtistAlias(String encodedStr, int artistId) throws IOException {
		
		System.out.println("dick!!!!!!!!!!!!!");
		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
        Path path = Paths.get(ARTISTPATH + "/" + artistId +  "/" + ARTWORKIMAGE);
        
        System.out.println(path);
        
        if(Files.notExists(path)) {
       	 	setArtistDirectory(artistId);
        }

        try{
        		
        		InputStream in = new ByteArrayInputStream(decodedBytes);
			
			BufferedImage bImageFromConvert = ImageIO.read(in);
					
			ImageIO.write(bImageFromConvert, "png", path.toFile()); // File might be null
            
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
