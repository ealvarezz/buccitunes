package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buccitunes.jsonmodel.LoginInfo;
import com.buccitunes.jsonmodel.SignupFormInfo;
import com.buccitunes.miscellaneous.*;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;
import com.buccitunes.service.UserService;

@Controller
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String hello() {
		return "index";
	}
	
	@RequestMapping(value="get_all_users", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers() {
		System.out.println(BucciConstant.MOONMAN_TIER_MONTHLY_PRICE);
		System.out.println(BucciConstant.NITRODUBS_TIER_MONTHLY_PRICE);
		return userService.findAll();
	}
	
	@RequestMapping(value="add_user", method = RequestMethod.POST)
	public @ResponseBody void addUser(@RequestBody User user) {
		userService.save(user);
	}
	
	@RequestMapping(value="delete_user", method = RequestMethod.GET)
	public @ResponseBody void removeUser(@RequestParam String email) {
		userService.remove(email);
	}
	
	@RequestMapping(value="follow", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> followUser(@RequestBody User followedUser, HttpSession session) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(BucciConstant.NOT_LOGGED_IN);
		}	
		try{
			User user = userService.follow(loggedUser.getEmail(), followedUser.getEmail());
			return BucciResponseBuilder.successMessage("You are now following " + user.getEmail()); 
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="get_followers", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<User>> getAllFollowers(@RequestParam String email) {
		List<User> users;
		try {
			users = userService.getFollowers(email);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage()); 
		}
		
		if(users.size() == 0) {
			return BucciResponseBuilder.successfulResponseMessage("There are no followers", users);
		} else {
			return BucciResponseBuilder.successfulResponse(users);
		}
		
	}
	
	@RequestMapping(value="get_following", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<User>> getAllFollowings(@RequestParam String email) {
		List<User> users;
		try {
			users = userService.getFollowing(email);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage()); 
		}
		
		if(users.size() == 0) {
			return BucciResponseBuilder.successfulResponseMessage("There is no user following", users);
		} else {
			return BucciResponseBuilder.successfulResponse(users);
		}
	}
	
	@RequestMapping(value="find_by_name", method = RequestMethod.GET)
	public @ResponseBody List<User> findUserByName(@RequestParam String name) {
		return userService.findByName(name);
	}
	
	@RequestMapping(value="update_name", method = RequestMethod.GET)
	public @ResponseBody void updateUserName(@RequestParam String email, @RequestParam String name) {
		userService.updateName(email, name);
	}
	
	@RequestMapping(value="signup", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> updateUserName(@RequestBody SignupFormInfo signupInfo) {
		try{
			User newUser = userService.signup(signupInfo);
			BucciResponse<User> success = BucciResponseBuilder.successfulResponseMessage("Successful Signup", newUser);
			return success;
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}	
	}
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> login(@RequestBody LoginInfo loginInfo, HttpSession session) {
		User loggedUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(loggedUser != null) {
			return BucciResponseBuilder.failedMessage("Already Logged In");
		}
		
		User account = userService.findOne(loginInfo.email);
		
		if(account != null && BucciPassword.checkPassword(loginInfo.password, account.getPassword())) {
			session.setAttribute(BucciConstant.SESSION, account);
			return BucciResponseBuilder.successfulResponseMessage("Successful Login", account);	
		} else {
			return BucciResponseBuilder.failedMessage("Invalid Login Information");
		}
	}
	
	@RequestMapping(value="logout", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> logout(@RequestBody User user, HttpSession session) {
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(BucciConstant.NOT_LOGGED_IN);
		} 
		if(!sessionUser.getEmail().equals(user.getEmail())) {
			return BucciResponseBuilder.failedMessage("Invalid Email");
		}
		session.removeAttribute(BucciConstant.SESSION);
		return BucciResponseBuilder.successMessage("LoggedOut");
	}
	
	@RequestMapping(value="logged_in", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<User> sessionTest(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			return BucciResponseBuilder.successfulResponse(sessionUser);
		}
	}
	
	@RequestMapping(value="saved_albums", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> getUserSavedAlbums(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			List<Album> savedAlbums = userService.getSavedAlbums(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(savedAlbums);
		}
	}
	
	@RequestMapping(value="followed_artists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Artist>> getUserFollowedArtist(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			List<Artist> followedArtists = userService.getFollowedArtist(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(followedArtists);
		}
	}
	
	@RequestMapping(value="followed_playlists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Playlist>> getUserFollowedPlaylists(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			List<Playlist> followedPlaylist = userService.getFollowedPlaylist(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(followedPlaylist);
		}
	}
	
	@RequestMapping(value="save_album", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> userSaveAlbum(HttpSession session, @RequestBody Album album) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			userService.saveAlbum(album.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("Album saved!");	
		}
	}
	
	@RequestMapping(value="save_song", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> userSaveSong(HttpSession session, @RequestBody Song song) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			userService.saveSong(song.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("Song saved!");	
		}
	}
	
	@RequestMapping(value="saved_songs", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Song>> getUserSavedSongs(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			List<Song> savedSongs = userService.getSavedSongs(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(savedSongs);	
		}
	}
	
	@RequestMapping(value="premium_upgrade", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<PremiumUser> premiumUpgrade(@RequestBody BillingInfo billingInfo, HttpSession session) {
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		try {
			PremiumUser user = userService.upgradeToPremium(sessionUser, billingInfo);
			session.setAttribute(BucciConstant.SESSION, user);
			sessionUser = user;
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
		return BucciResponseBuilder.successfulResponseMessage("Congratulations, you have upgraded to premium", (PremiumUser)sessionUser);
	}
	
	@RequestMapping(value="albums_of_saved_songs", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<PremiumUser> albumsOfSavedSongs(@RequestBody BillingInfo billingInfo, HttpSession session) {
		User sessionUser = (User) session.getAttribute(BucciConstant.SESSION);
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		/*
		try {
			return null;
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}*/
		return  BucciResponseBuilder.failedMessage("Not yet implement!");
		//return BucciResponseBuilder.successfulResponseMessage("Congratulations, you have upgraded to premium", (PremiumUser)sessionUser);
	}
	/*
	@RequestMapping(value="followingAlbums", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> followingAlbums(@RequestBody BillingInfo billingInfo, HttpSession session) {
		
	}
	*/
}
