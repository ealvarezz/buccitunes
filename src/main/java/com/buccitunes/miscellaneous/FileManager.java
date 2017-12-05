package com.buccitunes.miscellaneous;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Comparator;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import com.buccitunes.model.Playlist;
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
	private final static String DEFAULTMIMETYPESONG = "mp3";
	private final static String SONGAUDIO = "audio." + DEFAULTMIMETYPESONG;

	
	public static void setUpFileDirectory(Path newPath) throws IOException {
		if(Files.notExists(FILESPATH)) {
			Files.createDirectory(FILESPATH);
		}
		if(Files.notExists(newPath)) {
			Files.createDirectory(newPath);
		}
	}
	
	public static void createDirectory(Path parentPath, int id) throws IOException {
		if(Files.notExists(parentPath)) {
			setUpFileDirectory(parentPath);
		}
		
		Path path = Paths.get(parentPath.toString() + "/" + id + "/");
		if(Files.notExists(path)) {
			Files.createDirectory(path);
		}
	}
	
	public static void createDirectory(Path parentPath, String id) throws IOException {
		if(Files.notExists(parentPath)) {
			setUpFileDirectory(parentPath);
		}
		
		Path path = Paths.get(parentPath.toString() + "/" + id + "/");
		if(Files.notExists(path)) {
			Files.createDirectory(path);
		}
	}
	
	private static String getResourcePathString(String path) {
		String folderPath = path.substring(FILESPATH.toString().length()+1);
		return RESOURCEPATHALIAS + folderPath;
	}
	
	private static Path getAbsolutePathFromResourceString(String path) {
		String folderPath = path.toString().substring(RESOURCEPATHALIAS.length()-1);
		return Paths.get(FILESPATH.toString() + folderPath);	
	}
	
	public static String saveAlbumArtwork(String encodedStr, int albumId) throws IOException {
        Path path = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ALBUMPATH, albumId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String saveUserAvatar(String encodedStr, String userId) throws IOException {
        Path path = Paths.get(USERPATH + "/" + userId +  "/" + AVATARIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(USERPATH, userId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String saveArtistAvatar(String encodedStr, int artistId) throws IOException {
        Path path = Paths.get(ARTISTPATH + "/" + artistId +  "/" + AVATARIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(ARTISTPATH, artistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String savePlaylistArtwork(String encodedStr, int playlistId) throws IOException {
		Path path = Paths.get(PLAYLISTPATH + "/" + playlistId +  "/" + ARTWORKIMAGE);
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(PLAYLISTPATH, playlistId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath;
	}
	
	public static String saveRequestedAlbumArtwork(String encodedStr, int requestedId) throws IOException {
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + requestedId +  "/" + ARTWORKIMAGE);
        
        if(Files.notExists(path)) {
        	createDirectory(REQUESTEDALBUMSPATH, requestedId);
        }
        
        String strPath = saveImage(encodedStr, path);
        return strPath; 
	}
	
	public static String saveSong(String encodedStr, int songId) throws IOException {
		Path path = Paths.get(SONGPATH + "/" + songId +  "/" + SONGAUDIO );
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(SONGPATH, songId);
        }
        
        String strPath = saveAudio(encodedStr, path);
        return strPath;
        
	}
	
	public static String saveRequestedSong(String encodedStr, int requestedId) throws IOException {
		Path path = Paths.get(REQUESTEDSONGSMPATH + "/" + requestedId +  "/" + SONGAUDIO );
        System.out.println(path);
        
        if(Files.notExists(path)) {
        	createDirectory(REQUESTEDSONGSMPATH, requestedId);
        }
        
        String strPath = saveAudio(encodedStr, path);
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
	
	private static String saveAudio(String encodedStr, Path path) throws IOException {
		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
		Files.write(path, decodedBytes);
		
		return getResourcePathString(path.toString());
	}
	
	public static String moveRequestedArtworkToAlbum(String requestedPath, int albumId) throws BucciException, IOException {
		Path oldPath = getAbsolutePathFromResourceString(requestedPath);
		
		if(Files.notExists(oldPath)) {
        	throw new BucciException("'"+ oldPath + "' does not exist");
        }
		
		Path newPath = Paths.get(ALBUMPATH + "/" + albumId +  "/" + ARTWORKIMAGE);
		
		if(Files.notExists(newPath)) {
        	createDirectory(ALBUMPATH, albumId);
        }
		System.out.println("Moving From " + oldPath + " 	To 		" + newPath);
		
		String strPath = moveFile(oldPath, newPath);
		return strPath;
	}
	
	public static String moveRequestedArtworkToSong(String requestedPath, int songId) throws BucciException, IOException {
		Path oldPath = getAbsolutePathFromResourceString(requestedPath);
		
		if(Files.notExists(oldPath)) {
        	throw new BucciException("'"+ oldPath + "' does not exist");
        }
		
		Path newPath = Paths.get(SONGPATH + "/" + songId +  "/" + ARTWORKIMAGE);
		
		if(Files.notExists(newPath)) {
        	createDirectory(SONGPATH, songId);
        }
		System.out.println("Moving From " + oldPath + " 	To 		" + newPath);
		
		String strPath = moveFile(oldPath, newPath);
		return strPath;
	}
	
	public static String moveRequestedAudio(String requestedPath, int songId) throws BucciException, IOException {
		Path oldPath = getAbsolutePathFromResourceString(requestedPath);
		
		if(Files.notExists(oldPath)) {
        	throw new BucciException("'"+ oldPath + "' does not exist");
        }
		
		Path newPath = Paths.get(SONGPATH + "/" + songId +  "/" + SONGAUDIO);
		
		if(Files.notExists(newPath)) {
        	createDirectory(SONGPATH, songId);
        }
		System.out.println("Moving From " + oldPath + " 	To 		" + newPath);
		
		String strPath = moveFile(oldPath, newPath);
		return strPath;
	}
	
	private static String moveFile(Path oldPath, Path newPath) throws IOException {
		Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
		return getResourcePathString(newPath.toString());
	}
	
	public static void removePlaylistResources(Playlist playlist) throws IOException {
		int id = playlist.getId();
		Path path = Paths.get(PLAYLISTPATH + "/" + id +  "/");
		if(Files.exists(path)) {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			playlist.setArtworkPath(null);
		}
	}
	
	public static void removeRequestedAlbumResources(RequestedAlbum album) throws IOException {
		int id = album.getId();
		Path path = Paths.get(REQUESTEDALBUMSPATH + "/" + id +  "/");
		if(Files.exists(path)) {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			album.setArtworkPath(null);
		}
		
		for(RequestedSong song : album.getSongs()) {
			id = song.getId();
			path = Paths.get(REQUESTEDSONGSMPATH + "/" + id +  "/");
			if(Files.exists(path)) {
				Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				song.setPicturePath(null);
				song.setAudioPath(null);
			}
		}
	}
	
	public static void removeRequestedSongResources(RequestedSong song) throws IOException {
	 	int id = song.getId();
		Path path = Paths.get(REQUESTEDSONGSMPATH + "/" + id +  "/");
		if(Files.exists(path)) {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			song.setPicturePath(null);
			song.setAudioPath(null);
		}
	}
	
	public static void removeFileByStringPath(String strPath) throws IOException {
		Path path = getAbsolutePathFromResourceString(strPath);
		if(Files.exists(path)) {
			Files.delete(path);
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
