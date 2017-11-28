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

import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.User;
import com.buccitunes.model.ArtistUser;
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
		try {
			ArtistUser artist = adminService.adminApproveArtist(requested);
			return BucciResponseBuilder.successfulResponseMessage("New Artist Added", artist);
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
	}
	
	@RequestMapping(value="add_album_admin", method = RequestMethod.POST)
	public BucciResponse<Album> addAlbum(@RequestBody RequestedAlbum requested, HttpSession session) {
		try {
			Album album = adminService.addAlbum(requested);
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
	
	@RequestMapping(value="requestedalbums", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<RequestedAlbum>> getRequestedAlbums() {
		List<RequestedAlbum> requested = adminService.getRequestedAlbums();
		return BucciResponseBuilder.successfulResponse(requested);
	}
	
	
	
	
}
