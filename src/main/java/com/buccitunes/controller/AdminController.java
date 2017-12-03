package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.User;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.service.AdminService;


@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="add_artist", method = RequestMethod.POST)
	public BucciResponse<Artist> addArtist(@RequestBody Artist artist, HttpSession session) throws BucciException {
		Artist newArtist = adminService.addNewArtist(artist);
		return BucciResponseBuilder.successfulResponseMessage("New Artist Added", newArtist);
	}
	
	@RequestMapping(value="approve_artist", method = RequestMethod.POST)
	public BucciResponse<ArtistUser> approveArtist(@RequestBody RequestedArtist requested, HttpSession session) {
		/*
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		} else if(BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage("You do not have permissions!");
		}
		*/
		
		try {
			ArtistUser artist = adminService.adminApproveArtist(requested);
			return BucciResponseBuilder.successfulResponseMessage("New Artist Added", artist);
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="add_song", method = RequestMethod.POST)
	public BucciResponse<Song> addSongToAlbum(@RequestBody Song song, HttpSession session) {
		/*
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		} else if(BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage("You do not have permissions!");
		}
		*/
		
		try {
			Song newSong = adminService.addSongToAlbum(song);
			return BucciResponseBuilder.successfulResponseMessage("New Album Added", newSong);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="add_album_admin", method = RequestMethod.POST)
	public BucciResponse<Album> addAlbum(@RequestBody Album albumToAdd, HttpSession session) {
		/*User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		} else if(BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage("You do not have permissions!");
		}
		*/
		try {
			Album album = adminService.addAlbum(albumToAdd);
			return BucciResponseBuilder.successfulResponseMessage("New Album Added", album);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="approve_album", method = RequestMethod.POST)
	public BucciResponse<Album> approveAlbum(@RequestBody RequestedAlbum requested, HttpSession session) {
		try {
			Album album = adminService.adminApproveAlbum(requested);
			return BucciResponseBuilder.successfulResponseMessage("New Album Added", album);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="approvesong", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<Song> approveSong(RequestedSong requested) {
		try {
			Song song = adminService.adminApproveSong(requested);
			return BucciResponseBuilder.successfulResponse(song);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="get_playsongs_this_month", method = RequestMethod.GET)
	public BucciResponse<List<SongPlays>> getAllPlayedSongsThisMonth(@RequestParam int artist_id) {		
		List<SongPlays> plays = adminService.getSongPLays(artist_id);
		return BucciResponseBuilder.successfulResponse(plays);
	}
	
	@RequestMapping(value="pay_royalties", method = RequestMethod.GET)
	public BucciResponse<Double> payRoyaltiesToArtists() {
		double totalPaid = adminService.payRoyalties();
		return BucciResponseBuilder.successfulResponse(new Double(totalPaid));
	}
	
	@RequestMapping(value="requested_albums", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<RequestedAlbum>> getRequestedAlbums() {
		List<RequestedAlbum> requested = adminService.getRequestedAlbums();
		return BucciResponseBuilder.successfulResponse(requested);
	}
	
	@RequestMapping(value="requested_album_info", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<RequestedAlbum> getRequestedAlbum(@RequestParam int id) {
		try {
			RequestedAlbum requested = adminService.getRequestedAlbum(id);
			return BucciResponseBuilder.successfulResponse(requested);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="disapprove_request_album", method = RequestMethod.DELETE)
	public BucciResponse<String> deleteRequestAlbum(@RequestBody RequestedAlbum requestedAlbum) {		
		try {
			adminService.removeRequestedAlbum(requestedAlbum);
			return BucciResponseBuilder.successMessage("Requested album deleted");
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="disapprove_request_song", method = RequestMethod.DELETE)
	public BucciResponse<String> deleteRequestedSong(@RequestBody RequestedSong requestedSong) {		
		try {
			adminService.removeRequestedSong(requestedSong);
			return BucciResponseBuilder.successMessage("Requested song deleted");
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
		
	}
	
	@RequestMapping(value="charge_this_month's_users", method = RequestMethod.GET)
	public BucciResponse<List<PremiumUser>> chargeUsers() {
		return null;
	}
}
