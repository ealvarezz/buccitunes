package com.buccitunes.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.buccitunes.resultset.AlbumWithStats;
import com.buccitunes.service.MusicCollectionService;
import com.buccitunes.service.UserService;
@Configuration
@PropertySource("classpath:config.properties")

@Controller
public class MusicCollectionController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MusicCollectionService musicCollectionService;
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="get_new_releases", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> newReleasedAlbums() {
		List<Album> newAlbums = musicCollectionService.getNewReleasesByCurrentMonth();
		return BucciResponseBuilder.successfulResponse(newAlbums);
	}
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="top_albums_of_week", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> topAlbumsByWeek() {
		List<Album> newAlbums = musicCollectionService.getTopAlbumsByWeek();
		return BucciResponseBuilder.successfulResponse(newAlbums);
	}
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="top_playlists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Playlist>> topPlaylistsOfAllTime() {
		List<Playlist> topPlaylists = musicCollectionService.getTopPlaylist();
		return BucciResponseBuilder.successfulResponse(topPlaylists);
	}
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="top_playlists_by_genre", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Playlist>> topPlaylistsByGenre(@RequestParam int genreId) {
		List<Playlist> topPlaylists = musicCollectionService.getTopPlaylistByGenre(genreId);
		return BucciResponseBuilder.successfulResponse(topPlaylists);
	}
	
	
	@RequestMapping(value="playlist", method = RequestMethod.GET)
	public @ResponseBody Playlist getPlaylist(@RequestParam int id) {
		return musicCollectionService.getPlaylist(id);
	}
	
	@RequestMapping(value="new_playlist", method = RequestMethod.POST)
	public @ResponseBody  BucciResponse<Playlist> getPlaylist(@RequestBody Playlist playlist, HttpSession session) {
		
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		
		playlist.setOwner(loggedUser);
		
		try {
			Playlist newPlaylist = musicCollectionService.newPlaylist(playlist);
			return BucciResponseBuilder.successfulResponseMessage("New playlist created", newPlaylist);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		} 
	}
	
	/*
	@RequestMapping(value="addSongsToPlaylist", method = RequestMethod.POST)
	public @ResponseBody Playlist getPlaylist(@RequestBody Playlist song) {
		return musicCollectionService.getPlaylist(id);
	}*/
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="top_albums_by_genre", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> topAlbumByGenre(@RequestParam int genreId) {
		List<Album> topAlbums = musicCollectionService.getTopAlbumsByGenre(genreId);
		return BucciResponseBuilder.successfulResponse(topAlbums);
	}
	
	@RequestMapping(value="album", method = RequestMethod.GET)
	public @ResponseBody Album getAlbum(@RequestParam int id) {
		return musicCollectionService.getAlbum(id);
	}
	

	@RequestMapping(value="album_with_no_songs", method = RequestMethod.GET)
	public @ResponseBody Album getAlbumWithNoSongs(@RequestParam int id) {
		return musicCollectionService.getAlbumNoSongs(id);
	}
	
	@Cacheable(value="popularityCache")
	@RequestMapping(value="get_top_songs", method = RequestMethod.GET)
	public @ResponseBody List<Song> topSongs() {
		
		return musicCollectionService.getTopSongs();
	}
	
	/*
	@Cacheable(value="popularityCache")
	@RequestMapping(value="gettopsongsbyartist", method = RequestMethod.GET)
	public @ResponseBody List<Song> topSongsByArtist(@RequestParam int artistId) {
		
		return musicCollectionService.getTopSongsByArtist(artistId);
	}
	*/
	
	//For testing purposes
	@RequestMapping(value="check_date", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> check(@RequestBody Album album) {
		System.out.println(album.getReleaseDate());
		return BucciResponseBuilder.successfulResponse(album);
	}
	
	@RequestMapping(value="play_song", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<Song> playCurrentSong(@RequestParam String userId, int songId) {
		Song bucciSong;
		try {
			bucciSong = musicCollectionService.PlaySong(userId, songId);
			return BucciResponseBuilder.successfulResponse(bucciSong);
		} catch (ParseException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage());
		}
	}
	
	//For testing purposes
	@RequestMapping(value="add_album", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> addArtistAlbum(@RequestBody Album album) {
		try {
			musicCollectionService.saveAlbum(album);
			return BucciResponseBuilder.successfulResponse(album);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage()); 
		}
	}
	
	@RequestMapping(value="get_album_of_song", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Album> addArtistAlbum(@RequestBody Song song) {
		try {
			Album album = musicCollectionService.albumOfSong(song);
			return BucciResponseBuilder.successfulResponse(album);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage()); 
		}
	}
	
	@RequestMapping(value="change_tier", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<String> changeUserTier(@RequestParam String email, @RequestParam int tiercode) {
		try {
			musicCollectionService.assignTierToArtistUser(email, Tier.fromCode(tiercode));
			return BucciResponseBuilder.successfulResponse("Switched tier");
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getMessage()); 
		}
	}
	
	@RequestMapping(value="get_featured_albums", method = RequestMethod.GET)
	public @ResponseBody List<Album> getFeaturedAlbums(@RequestParam String email) {
		
		return musicCollectionService.getFeaturedAlbums(email);
	}
	
	@Scheduled(fixedRate=60000)
	@CacheEvict(allEntries=true, cacheNames={"${}"})
	public void clearPopularCache(){}
}
