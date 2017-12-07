package com.buccitunes.controller;

import java.util.List;

import javax.mail.MessagingException;
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
import com.buccitunes.miscellaneous.MailManager;
import com.buccitunes.model.AdminUser;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.User;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Concert;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedConcert;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.service.AdminService;


@RestController
public class AdminController {
	
	@Autowired
	private BucciConstants constants;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MailManager mailManager;
	
	@RequestMapping(value="add_artist", method = RequestMethod.POST)
	public BucciResponse<Artist> addArtist(@RequestBody Artist artist, HttpSession session) throws BucciException {
		Artist newArtist = adminService.addNewArtist(artist);
		return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), newArtist);
	}
	
	@RequestMapping(value="approve_artist", method = RequestMethod.POST)
	public BucciResponse<ArtistUser> approveArtist(@RequestBody RequestedArtist requested, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			ArtistUser artist = adminService.adminApproveArtist(requested);
			mailManager.mailApprovedArtistRequest(artist);
			return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), artist);
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="ban_user", method = RequestMethod.POST)
	public BucciResponse<Boolean> banUser(@RequestBody Artist User, HttpSession session) throws BucciException {
		//TODO
		return null;
	}
	
	@RequestMapping(value="add_song", method = RequestMethod.POST)
	public BucciResponse<Song> addSongToAlbum(@RequestBody Song song, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			Song newSong = adminService.addSongToAlbum(song);
			return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), newSong);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="add_album_admin", method = RequestMethod.POST)
	public BucciResponse<Album> addAlbum(@RequestBody Album albumToAdd, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			Album album = adminService.addAlbum(albumToAdd);
			return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), album);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="approve_album", method = RequestMethod.POST)
	public BucciResponse<Album> approveAlbum(@RequestBody RequestedAlbum requested, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			Album album = adminService.adminApproveAlbum(requested);
			mailManager.mailApprovedAlbumRequest(requested.getRequester(), album);
			return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), album);
		} catch (BucciException | MessagingException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}		
	}
	
	@RequestMapping(value="approve_concert", method = RequestMethod.POST)
	public BucciResponse<Concert> approveConcert(@RequestBody RequestedConcert requested, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		requested = adminService.getRequestedConcert(requested.getId());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			Concert concert = adminService.adminApproveConcert(requested);
			mailManager.mailApprovedConcertRequest(requested.getRequester(), concert);
			return BucciResponseBuilder.successfulResponseMessage(constants.getSuccessfulAdditionMsg(), concert);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	
	@RequestMapping(value="approve_song", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<Song> approveSong(RequestedSong requested, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			Song song = adminService.adminApproveSong(requested);
			mailManager.mailApprovedSongRequest(requested.getRequester(), song);
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
	
	
	@RequestMapping(value="requested_albums", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<RequestedAlbum>> getRequestedAlbums() {
		List<RequestedAlbum> requested = adminService.getRequestedAlbums();
		return BucciResponseBuilder.successfulResponse(requested);
	}
	
	@RequestMapping(value="requested_album_info", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<RequestedAlbum> getRequestedAlbum(@RequestParam int id, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			RequestedAlbum requested = adminService.getRequestedAlbum(id);
			return BucciResponseBuilder.successfulResponse(requested);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="disapprove_request_album", method = RequestMethod.POST)
	public BucciResponse<String> deleteRequestAlbum(@RequestBody RequestedAlbum requestedAlbum, HttpSession session) {		
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			adminService.removeRequestedAlbum(requestedAlbum);
			mailManager.mailDeniedAlbumRequest(requestedAlbum.getRequester(), requestedAlbum);
			return BucciResponseBuilder.successMessage(constants.getSuccessfulDeletionMsg());
		} catch (BucciException | MessagingException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value="disapprove_request_concert", method = RequestMethod.POST)
	public BucciResponse<String> deleteRequestAlbum(@RequestBody RequestedConcert requestedConcert, HttpSession session) {		
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			adminService.removeRequestedConcert(requestedConcert);
			mailManager.mailDeniedConcertRequest(requestedConcert.getRequester(), requestedConcert);
			return BucciResponseBuilder.successMessage(constants.getSuccessfulDeletionMsg());
		} catch (BucciException | MessagingException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value="disapprove_request_song", method = RequestMethod.POST)
	public BucciResponse<String> deleteRequestedSong(@RequestBody RequestedSong requestedSong, HttpSession session) {		
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} else if(!BucciPrivilege.isAdmin(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getAdminAccessDeniedMsg());
		}
		try {
			adminService.removeRequestedSong(requestedSong);
			mailManager.mailDeniedSongRequest(requestedSong.getRequester(), requestedSong);
			return BucciResponseBuilder.successMessage(constants.getSuccessfulDeletionMsg());
		} catch (BucciException | MessagingException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value="charge_users", method = RequestMethod.GET)
	public BucciResponse<String> chargeUsers() {
		adminService.chargeUsers();
		return BucciResponseBuilder.successMessage(constants.getSuccessfulChargeMsg());
	}
	
	@RequestMapping(value="pay_royalties", method = RequestMethod.PUT)
	public BucciResponse<String> payArtistRoyalties() {
		adminService.payRoyaltiesByCaching();
		return BucciResponseBuilder.successMessage(constants.getSuccessfulChargeMsg());
	}
	
	@RequestMapping(value="add_admin", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<AdminUser> addAdmin(@RequestBody AdminUser user, HttpSession session) {
		
		try {
			AdminUser newUser = adminService.createAdminUser(user);
			return BucciResponseBuilder.successfulResponseMessage("Successful Signup", newUser);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
		
	}
}
