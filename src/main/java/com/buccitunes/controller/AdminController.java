package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.service.AdminService;


@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="addArtist", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Artist> addArtist(@RequestBody Artist artist, HttpSession session) throws BucciException {
		artist = adminService.addNewArtist(artist);
		return BucciResponseBuilder.successfulResponseMessage("New Artist Added", artist);
	}
	
	@RequestMapping(value="approveArtist", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<ArtistUser> approveArtist(@RequestBody RequestedArtist requested, HttpSession session) {
		ArtistUser artist;
		try {
			artist = adminService.adminApproveArtist(requested);
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}		
		return BucciResponseBuilder.successfulResponseMessage("New Artist Added", artist);
	}
	
	@RequestMapping(value="approveAlbum", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> approveAlbum(@RequestBody RequestedAlbum requested, HttpSession session) {
		Album album = adminService.adminApproveAlbum(requested);		
		return BucciResponseBuilder.successfulResponseMessage("New Album Added", album);
	}
}
