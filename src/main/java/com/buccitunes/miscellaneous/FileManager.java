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
	
	private final static String RESOURCEPATHALIAS = "/resources/";
	private final static String FILESDIR = "/BucciTunesFiles/";
	private final static Path FILESPATH = Paths.get(System.getProperty("user.home").toString() + FILESDIR);
	private final static Path USERPATH = Paths.get(FILESPATH.toString() + "/USERS/");
	private final static Path ARTISTPATH = Paths.get(FILESPATH.toString() + "/ARTISTS/");
	private final static Path ALBUMPATH = Paths.get(FILESPATH.toString() + "/ALBUMS/");
	private final static Path PLAYLISTPATH = Paths.get(FILESPATH.toString() + "/PLAYLISTS/");
	private final static Path SONGPATH = Paths.get(FILESPATH.toString() + "/SONGS/");
	private final static String ARTWORKIMAGE = "artwork.png";
	private final static String AVATARIMAGE = "avatar.png";

	
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
	
	public static void setAlbumDirectory(int albumId) throws IOException {
		
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path albumDir = Paths.get(ALBUMPATH.toString() + "/" + albumId + "/");
		if(Files.notExists(albumDir)) {
			Files.createDirectory(albumDir);
		}
	}
	
	public static void setPlaylistDirectory(int playlistId) throws IOException {
			
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path playlistDir = Paths.get(PLAYLISTPATH.toString() + "/" + playlistId + "/");
		if(Files.notExists(playlistDir)) {
			Files.createDirectory(playlistDir);
		}
	}
	
	private static String createResourcePathString(String path) {
		String folderPath = path.substring(FILESPATH.toString().length()+1);
		return RESOURCEPATHALIAS + folderPath;
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
         
         return createResourcePathString(path.toString());
	}
	
	public static String saveAlbumAlias(String encodedStr, int albumId) throws IOException {
        Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	setAlbumDirectory(albumId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String saveArtistAlias(String encodedStr, int artistId) throws IOException {
        Path path = Paths.get(ARTISTPATH + "/" + artistId +  "/" + AVATARIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
       	 	setArtistDirectory(artistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        System.out.println("RESOURCE : " + strPath);
        return strPath; 
	}
	
	public static String savePlaylistAlias(String encodedStr, int playlistId) throws IOException {
		Path path = Paths.get(PLAYLISTPATH + "/" + playlistId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	setPlaylistDirectory(playlistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath;
	}
	
	private static String saveImage(String encodedStr, Path path) {
		
		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
		 try{     		
			 InputStream in = new ByteArrayInputStream(decodedBytes);
			 BufferedImage bImageFromConvert = ImageIO.read(in);
			 ImageIO.write(bImageFromConvert, "png", path.toFile()); // File might be null
			
		 } catch (Exception ex) {
			 ex.printStackTrace();
	     }
	        
	     return createResourcePathString(path.toString());
	}
	
	public static String getFilesPath() {
		return FILESPATH.toString() + "/";
	}
	
	public static String getFilesAliasPath() {
		return FILESPATH.toString() + "/";
	}
	
	public static String getResourcePath() {
		return RESOURCEPATHALIAS;
	}
	
	public static String getResourceAliasPath() {
		return RESOURCEPATHALIAS + "**";
	}
}
