package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buccitunes.miscellaneous.BucciConstant;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;
import com.buccitunes.service.ArtistService;
import com.buccitunes.service.UserService;


	
@RestController	
public class ArtistController {
	
	@Autowired
	private ArtistService artistService;
	
	@RequestMapping(value="get_artist_by_name", method = RequestMethod.POST)
	public BucciResponse<Artist> findArtistByName(@RequestBody String name) {
		try {
			Artist artist = artistService.getArtistByName(name);
			return BucciResponseBuilder.successfulResponse(artist);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="add_artist_user", method = RequestMethod.POST)
	public BucciResponse<ArtistUser> addArtistUser(@RequestBody ArtistUser artistUser) {
		try {
			ArtistUser newArtistUser = artistService.saveArtistUser(artistUser);
			return BucciResponseBuilder.successfulResponse(newArtistUser);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="artist", method = RequestMethod.GET)
	public BucciResponse<Artist> getArtist(@RequestParam int id) {		
		try {
			Artist artist = artistService.getArtist(id);
			return BucciResponseBuilder.successfulResponse(artist);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="top_songs_of_artist", method = RequestMethod.GET)
	public BucciResponse<List<Song>> getTopSongsOfArtist(@RequestParam int id) {
		 List<Song> songs = artistService.getTopTenSongs(id);
		 return BucciResponseBuilder.successfulResponse(songs);
	}
	
	@RequestMapping(value="request_album", method = RequestMethod.POST)
	public BucciResponse<RequestedAlbum> requestAnAlbum(@RequestBody RequestedAlbum requested, HttpSession session) {
		
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		
		if(BucciPrivilege.isArtist(loggedUser)) {
			RequestedAlbum newRequestedAlbum;
			
			try {
				newRequestedAlbum = artistService.requestNewAlbum(requested, ((ArtistUser) loggedUser));
			} catch (BucciException e) {
				return BucciResponseBuilder.failedMessage(e.getErrMessage());
			}
			
			return BucciResponseBuilder.successfulResponseMessage("Album request was submitted", newRequestedAlbum);
		} else {
			return BucciResponseBuilder.failedMessage("You must be an artist in order to request an album");
		}
	}
	
	@RequestMapping(value="request_song", method = RequestMethod.POST)
	public BucciResponse<String> requestSongToAlbum(@RequestBody RequestedSong requested, HttpSession session) {
		
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		
		if(BucciPrivilege.isArtist(loggedUser)) {
			
			artistService.addSongToAlbum(requested);
			
			return BucciResponseBuilder.successfulResponse("Song request was submitted");
		} else {
			return BucciResponseBuilder.failedMessage("You must be an artist in order to request an album");
		}
	}
	
	@RequestMapping(value="delete_album", method = RequestMethod.DELETE)
	public BucciResponse<String> requestSongToAlbum(@RequestParam int albumId, HttpSession session) {
		
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		
		if(BucciPrivilege.isArtist(loggedUser) || BucciPrivilege.isAdmin(loggedUser)) {
			
			artistService.deleteAlbum(albumId);
			
			return BucciResponseBuilder.successfulResponse("Song request was submitted");
		} else {
			return BucciResponseBuilder.failedMessage("You must be an artist in order to request an album");
		}
	}
	
	@RequestMapping(value="delete_song_album", method = RequestMethod.DELETE)
	public BucciResponse<String> requestSongToAlbum(@RequestParam int albumId, @RequestParam int songId, HttpSession session) {
		
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		
		if(BucciPrivilege.isArtist(loggedUser) || BucciPrivilege.isAdmin(loggedUser)) {
			
			artistService.deleteSongFromAlbum(songId, albumId);
			
			return BucciResponseBuilder.successfulResponse("Song request was submitted");
		} else {
			return BucciResponseBuilder.failedMessage("You must be an artist in order to request an album");
		}
	}
}
