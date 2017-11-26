package com.buccitunes.miscellaneous;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;

public class FileManager {
	
	private final static String RESOURCEPATHALIAS = "/resources/";
	private final static String FILESDIR = "/BucciTunesFiles/";
	private final static Path FILESPATH = Paths.get(System.getProperty("user.home").toString() + FILESDIR);
	private final static Path USERPATH = Paths.get(FILESPATH.toString() + "/USERS/");
	private final static Path ARTISTPATH = Paths.get(FILESPATH.toString() + "/ARTISTS/");
	private final static Path ALBUMPATH = Paths.get(FILESPATH.toString() + "/ALBUMS/");
	private final static Path PLAYLISTPATH = Paths.get(FILESPATH.toString() + "/PLAYLISTS/");
	private final static Path SONGPATH = Paths.get(FILESPATH.toString() + "/SONGS/");
	private final static Path REQUESTEDALBUMSPATH = Paths.get(FILESPATH.toString() + "/REQUESTED ALBUMS/");
	private final static Path REQUESTEDSONGSMPATH = Paths.get(FILESPATH.toString() + "/REQUESTED SONGS/"); 
	private final static String DEFAULTMIMETYPEIMAGE = "png";
	private final static String ARTWORKIMAGE = "artwork." + DEFAULTMIMETYPEIMAGE;
	private final static String AVATARIMAGE = "avatar." + DEFAULTMIMETYPEIMAGE;

	
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
	
	public static void createDirectory(Path path, int id) throws IOException {
		if(Files.notExists(FILESPATH)) {
			setUpFileDirectory();
		}
		
		Path dir = Paths.get(path.toString() + "/" + id + "/");
		if(Files.notExists(dir)) {
			Files.createDirectory(dir);
		}
	}
	
	private static String getResourcePathString(String path) {
		String folderPath = path.substring(FILESPATH.toString().length()+1);
		return RESOURCEPATHALIAS + folderPath;
	}
	
	private static Path getPathStringFromResource(Path path) {
		String folderPath = path.toString().substring(RESOURCEPATHALIAS.length()+1);
		return Paths.get(FILESPATH.toString() + folderPath);
		
	}
	
	public static String saveArtwork(String encodedStr, int albumId) throws IOException {
		
		 byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
		 
		 System.out.println("I  MADE IT HERE!!!!!!!!!!!!!!!!!!!!!!!!\n\n\n\n\n");
         Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
         
         System.out.println(path);
         
         if(Files.notExists(path)) {
        	 createDirectory(ALBUMPATH,albumId);
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
         
         return getResourcePathString(path.toString());
	}
	
	public static String saveAlbumAlias(String encodedStr, int albumId) throws IOException {
        Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ALBUMPATH, albumId);
        }
        
        String strPath = saveImage(encodedStr, path);
        
        return strPath; 
	}
	
	public static String moveRequestedToAlbumAlias(int requestedId, int albumId) throws BucciException, IOException {
		Path oldPath = Paths.get(REQUESTEDALBUMSPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
		System.out.println("Searching in " + oldPath);
		
		if(Files.notExists(oldPath)) {
        	throw new BucciException("'"+ oldPath + "' does not exist");
        }
		
		Path newPath = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
		System.out.println(newPath);
		
		if(Files.notExists(newPath)) {
        	createDirectory(ALBUMPATH, albumId);
        }
		
		String strPath = moveImage(oldPath, newPath);
		return strPath;
		
	}
	
	public static String saveArtistAlias(String encodedStr, int artistId) throws IOException {
        Path path = Paths.get(ARTISTPATH + "/" + artistId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ARTISTPATH, artistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String savePlaylistAlias(String encodedStr, int playlistId) throws IOException {
		Path path = Paths.get(PLAYLISTPATH + "/" + playlistId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(PLAYLISTPATH, playlistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath;
	}
	
	public static String saveRequestedAlbumAlias(String encodedStr, int requestedId) throws IOException {
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + requestedId +  "/" + AVATARIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(REQUESTEDALBUMSPATH, requestedId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	private static String saveImage(String encodedStr, Path path) {
		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
		 try{     		
			 InputStream in = new ByteArrayInputStream(decodedBytes);
			 BufferedImage bImageFromConvert = ImageIO.read(in);
			 ImageIO.write(bImageFromConvert, DEFAULTMIMETYPEIMAGE, path.toFile()); // File might be null
			
		 } catch (Exception ex) {
			 ex.printStackTrace();
	     }
	        
	     return getResourcePathString(path.toString());
	}
	
	private static String moveImage(Path oldPath, Path newPath) {
		return null;
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
