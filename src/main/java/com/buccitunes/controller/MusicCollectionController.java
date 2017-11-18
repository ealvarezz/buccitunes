package com.buccitunes.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.model.*;
import com.buccitunes.service.MusicCollectionService;
import com.buccitunes.service.UserService;

@Controller
public class MusicCollectionController {
	
	@Autowired
	private MusicCollectionService musicCollectionService;
	
	
	@RequestMapping(value="getNewReleases", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> newReleasedAlbums() {
		List<Album> newAlbums = musicCollectionService.getNewReleasesByCurrentMonth();
		return BucciResponseBuilder.successfulResponse(newAlbums);
	}
	
	
	@RequestMapping(value="topAlbumsOfweek", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> topAlbumsByWeek() {
		List<Album> newAlbums = musicCollectionService.getTopAlbumsByWeek();
		return BucciResponseBuilder.successfulResponse(newAlbums);
	}
	
	
	@RequestMapping(value="album", method = RequestMethod.GET)
	public @ResponseBody Album getAlbum(@RequestParam int id) {
		
		return musicCollectionService.getAlbum(id);
	}
	
	@RequestMapping(value="playlist", method = RequestMethod.GET)
	public @ResponseBody Playlist getPlaylist(@RequestParam int id) {
		
		return musicCollectionService.getPlaylist(id);
	}
	
	//For testing purposes
	@RequestMapping(value="checkDate", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> check(@RequestBody Album album) {
		System.out.println(album.getReleaseDate());
		return BucciResponseBuilder.successfulResponse(album);
	}
	
	@RequestMapping(value="playsong", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<Song> playCurrentSong(@RequestParam String userId, int songId) {
		
		Song bucciSong;
		try {
			bucciSong = musicCollectionService.PlaySong(userId, songId);
		} catch (ParseException e) {
			
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}
		return BucciResponseBuilder.successfulResponse(bucciSong);
	}
	
	//For testing purposes
	@RequestMapping(value="addalbum", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> addArtistAlbum(@RequestBody Album album) {
		
		try {
			
			musicCollectionService.saveAlbum(album);
			
		} catch (BucciException e) {
			
			return BucciResponseBuilder.failedMessage(e.getMessage()); 
		}
		
		return BucciResponseBuilder.successfulResponse(album);
	}
}
