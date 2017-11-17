package com.buccitunes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.service.ArtistService;
import com.buccitunes.service.UserService;


	
@Controller	
public class ArtistController {
	@Autowired
	private ArtistService artistService;
	
	
	@RequestMapping(value="getArtistByName", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Artist> findArtistByName(@RequestBody String name) {
		try {
			Artist artist = artistService.getArtistByName(name);
			return BucciResponseBuilder.successfulResponse(artist);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="addArtistUser", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<ArtistUser> addArtistUser(@RequestBody ArtistUser artistUser) {
		try {
			artistService.saveArtistUser(artistUser);
			return BucciResponseBuilder.successfulResponse(artistUser);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="artist", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<Artist> getArtist(@RequestParam int id) {
		try {
			Artist artist = artistService.getArtist(id);
			return BucciResponseBuilder.successfulResponse(artist);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
}
