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

import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedSong;

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

	
	public static void setUpFileDirectory(Path newPath) throws IOException {
		if(Files.notExists(FILESPATH)) {
			Files.createDirectory(FILESPATH);
		}
		if(Files.notExists(newPath)) {
			Files.createDirectory(newPath);
		}
	}
	
	public static void createDirectory(Path parentPath, Path path, int id) throws IOException {
		if(Files.notExists(parentPath)) {
			setUpFileDirectory(parentPath);
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
	
	private static Path getAbsolutePathFromResourceString(String path) {
		String folderPath = path.toString().substring(RESOURCEPATHALIAS.length()+1);
		return Paths.get(FILESPATH.toString() + folderPath);	
	}
	
	public static String saveAlbumArtwork(String encodedStr, int albumId) throws IOException {
        Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ALBUMPATH, path, albumId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String moveRequestedArtworkToAlbum(int requestedId, int albumId) throws BucciException, IOException {
		Path oldPath = Paths.get(REQUESTEDALBUMSPATH + "/" + requestedId +  "/" + ARTWORKIMAGE);
		
		if(Files.notExists(oldPath)) {
        	throw new BucciException("'"+ oldPath + "' does not exist");
        }
		
		Path newPath = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
		
		if(Files.notExists(newPath)) {
        	createDirectory(ALBUMPATH, newPath, albumId);
        }
		System.out.println("Moving From " + oldPath + " 	To 		" + newPath);
		
		String strPath = moveFile(oldPath, newPath);
		return strPath;
	}
	
	public static String saveArtistAvatar(String encodedStr, int artistId) throws IOException {
        Path path = Paths.get(ARTISTPATH + "/" + artistId +  "/" + AVATARIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ARTISTPATH, path, artistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String savePlaylistArtwork(String encodedStr, int playlistId) throws IOException {
		Path path = Paths.get(PLAYLISTPATH + "/" + playlistId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(PLAYLISTPATH, path, playlistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath;
	}
	
	public static String saveRequestedAlbumArtwork(String encodedStr, int requestedId) throws IOException {
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + requestedId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(REQUESTEDALBUMSPATH, path, requestedId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	/*
	public static String saveRequestedSongArtwork(String encodedStr, int requestedId) throws IOException {
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + requestedId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(REQUESTEDALBUMSPATH, path, requestedId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	*/
	
	private static String saveImage(String encodedStr, Path path) throws IOException {
		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
			
		InputStream in = new ByteArrayInputStream(decodedBytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, DEFAULTMIMETYPEIMAGE, path.toFile());
	
	    return getResourcePathString(path.toString());
	}
	
	private static String moveFile(Path oldPath, Path newPath) throws IOException {
		Files.move(oldPath, newPath);
		return getResourcePathString(newPath.toString());
	}
	
	public static void removeRequestedAlbumResources(RequestedAlbum album) throws IOException {
		int id = album.getId();
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + id +  "/");
		if(Files.exists(path)) {
			Files.delete(path);
			album.setArtworkPath(null);
		}
		
		for(RequestedSong song : album.getSongs()) {
			id = song.getId();
			path = Paths.get(REQUESTEDSONGSMPATH + "/" + id +  "/");
			if(Files.exists(path)) {
				Files.delete(path);
				song.setPicturePath(null);
			}
		}
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
