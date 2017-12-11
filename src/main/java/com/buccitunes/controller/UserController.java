package com.buccitunes.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buccitunes.jsonmodel.CurrentToNewForm;
import com.buccitunes.jsonmodel.LoginInfo;
import com.buccitunes.jsonmodel.SearchResults;
import com.buccitunes.jsonmodel.SignupFormInfo;
import com.buccitunes.jsonmodel.UserPageInfo;
import com.buccitunes.miscellaneous.*;
import com.buccitunes.model.AdminUser;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.Payment;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.Song;
import com.buccitunes.model.SupportTicket;
import com.buccitunes.model.User;
import com.buccitunes.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private BucciConstants constants;

	@Autowired
	private MailManager mailManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String hello() {
		return "index";
	}
	
	@RequestMapping(value="get_all_users", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<User>> getAllUsers() {
		/*try {
			ArtistUser ta = new ArtistUser();
			ta.setEmail("edwin.alvarez@stonybrook.edu");
			Album a = new Album();
			a.setTitle("fake album");
			RequestedAlbum b = new RequestedAlbum();
			b.setTitle("fake album");
			mailManager.sendApprovedAlbumRequest(ta,a);
			mailManager.sendDeniedAlbumRequestToUser(ta,b);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null; */
		return BucciResponseBuilder.successfulResponse(userService.findAll());
	}
	
	@RequestMapping(value="add_user", method = RequestMethod.POST)
	public @ResponseBody void addUser(@RequestBody User user) {
		userService.save(user);
		try {
			mailManager.mailAccountConfirmation(user);
		} catch (MessagingException e) {
			
		}
	}
	/*
	@RequestMapping(value="delete_user", method = RequestMethod.GET)
	public @ResponseBody void removeUser(@RequestParam String email) {
		userService.remove(email);
	}
	*/
	
	@RequestMapping(value="delete_user_account", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> deleteUser(@RequestBody LoginInfo loginInfo, HttpSession session) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		try{
			userService.deleteUser(loggedUser, loginInfo.password);
			return BucciResponseBuilder.successMessage("Goodbye friend, stay bucci"); 
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="follow", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> followUser(@RequestBody User followedUser, HttpSession session) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}	
		try{
			User user = userService.follow(loggedUser.getEmail(), followedUser.getEmail());
			return BucciResponseBuilder.successMessage("You are now following " + user.getEmail()); 
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="unfollow", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> unfollowUser(@RequestBody User followedUser, HttpSession session) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}	
		try{
			userService.unfollow(loggedUser.getEmail(), followedUser.getEmail());
			return BucciResponseBuilder.successMessage("Unfollowed user: " + followedUser.getEmail()); 
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
	public @ResponseBody BucciResponse<User> updateUserName(@RequestBody SignupFormInfo signupInfo, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser != null) {
			return BucciResponseBuilder.failedMessage("Already Logged In");
		}
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
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser != null) {
			return BucciResponseBuilder.failedMessage("Already Logged In");
		}
		
		User account = userService.findOne(loginInfo.email);
		
		if(account != null && account.passwordIsCorrect(loginInfo.password)) {
			session.setAttribute(constants.getSession(), account);
			return BucciResponseBuilder.successfulResponseMessage("Successful Login", account);	
		} else {
			return BucciResponseBuilder.failedMessage("Invalid Login Information");
		}
	}
	
	@RequestMapping(value="logout", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> logout(HttpSession session) {
		User sessionUser = (User) session.getAttribute(constants.getSession());
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		} 
		session.removeAttribute(constants.getSession());
		return BucciResponseBuilder.successMessage("LoggedOut");
	}
	
	@RequestMapping(value="logged_in", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<User> sessionTest(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			return BucciResponseBuilder.successfulResponse(sessionUser);
		}
	}
	
	@RequestMapping(value="saved_albums", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> getUserSavedAlbums(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
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
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			List<Artist> followedArtists = userService.getFollowedArtist(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(followedArtists);
		}
	}
	
	@RequestMapping(value="followed_playlists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Playlist>> getUserFollowedPlaylists(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			List<Playlist> followedPlaylist = userService.getFollowedPlaylist(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(followedPlaylist);
		}
	}
	
	@RequestMapping(value="save_album", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> userSaveAlbum(HttpSession session, @RequestBody Album album) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			userService.saveAlbum(album.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("Album saved!");	
		}
	}
	
	@RequestMapping(value="follow_playlist", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> followPlaylist(@RequestBody Playlist playlist, HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			userService.followPlaylist(playlist.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("User is now following playlist");	
		}
	}
	
	@RequestMapping(value="unfollow_playlist", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> unfollowPlaylist(@RequestBody Playlist playlist, HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			userService.unfollowPlaylist(playlist.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("Playlist unfollowed");	
		}
	}
	
	@RequestMapping(value="save_song", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> userSaveSong(HttpSession session, @RequestBody Song song) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			userService.saveSong(song.getId(), sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse("Song saved!");	
		}
	}
	
	@RequestMapping(value="saved_songs", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Song>> getUserSavedSongs(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			List<Song> savedSongs = userService.getSavedSongs(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(savedSongs);	
		}
	}
	
	@RequestMapping(value="premium_upgrade", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<PremiumUser> premiumUpgrade(@RequestBody BillingInfo billingInfo, HttpSession session) {
		User sessionUser = (User) session.getAttribute(constants.getSession());
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		try {
			PremiumUser user = userService.upgradeToPremium(sessionUser, billingInfo);
			session.setAttribute(constants.getSession(), user);
			sessionUser = user;
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
		return BucciResponseBuilder.successfulResponseMessage("Congratulations, you have upgraded to premium", (PremiumUser)sessionUser);
	}
	
	@RequestMapping(value="albums_of_saved_songs", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<PremiumUser> albumsOfSavedSongs(@RequestBody BillingInfo billingInfo, HttpSession session) {
		User sessionUser = (User) session.getAttribute(constants.getSession());
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		/*
		try {
			return null;
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}*/
		return  BucciResponseBuilder.failedMessage("Not yet implement!");
	}
	/*
	@RequestMapping(value="followingAlbums", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> followingAlbums(@RequestBody BillingInfo billingInfo, HttpSession session) {
		
	}
	*/
	
	@RequestMapping(value="recently_played", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> getUserRecentlyPlayedAlbums(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			List<Album> recentlyPlayedAlbums = userService.getRecentAlbumsPlayed(sessionUser.getEmail());
			return BucciResponseBuilder.successfulResponse(recentlyPlayedAlbums);	
		}
	}
	
	@RequestMapping(value="user_playlists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Playlist>> getUserPlaylists(HttpSession session) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else {
			try {
				List<Playlist> userPlaylists = userService.getUserPlaylists(sessionUser);
				return BucciResponseBuilder.successfulResponse(userPlaylists);		
			}
			catch(BucciException e) {
				return BucciResponseBuilder.failedMessage("There was an issue with your request");
			}
			
		}
	}
	
	@RequestMapping(value="search", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<SearchResults> search(HttpSession session, @RequestParam String searchString) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			SearchResults searchResults = userService.search(searchString);
			return BucciResponseBuilder.successfulResponse(searchResults);	
		}
	}
	
	@RequestMapping(value="get_related_artists", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Artist>> search(HttpSession session, @RequestParam int artistId) {	
		User sessionUser = (User) session.getAttribute(constants.getSession());
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		else {
			List<Artist> artists = userService.getRelatedArtists(artistId);
			return BucciResponseBuilder.successfulResponse(artists);	
		}
	}
	
	@RequestMapping(value="user", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<UserPageInfo> getUserInfo(@RequestBody String id, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		try {
			UserPageInfo userPage = userService.getUserInfo(id, loggedUser);
			return BucciResponseBuilder.successfulResponse(userPage);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="change_settings", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> changeUserInfo(@RequestBody User user, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		try {
			User changedUser = userService.changeUserInfo(loggedUser, user);
			session.setAttribute(constants.getSession(), changedUser);
			return BucciResponseBuilder.successfulResponse(changedUser);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="reset_password_send_email", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Boolean> resetPasswordSendEmail(@RequestBody String email) {
		User user = userService.findOne(email);
		if(user == null) {
			return BucciResponseBuilder.failedMessage("No account for this email.");
		}
		try {
			java.util.Base64.Encoder encoder = java.util.Base64.getUrlEncoder();
			mailManager.sendPasswordReset(email, encoder.encodeToString(user.getPassword().getBytes()));
			//mailManager.sendPasswordReset(email, user.getPassword());
			return BucciResponseBuilder.successfulResponseMessage("An email to reset your password has been sent to this email.",new Boolean(true));
		} catch (MessagingException e) {
			return BucciResponseBuilder.failedMessage("The email server is down, wait some time and try again.");
		}
	}
	
	@RequestMapping(value="check_reset_password_link", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Boolean> checkResetPasswordLink(@RequestBody LoginInfo info) {
		User user = userService.findOne(info.email);
		java.util.Base64.Encoder encoder = java.util.Base64.getUrlEncoder();

		if(user == null || !encoder.encodeToString(user.getPassword().getBytes()).equals(info.password)) {
			return BucciResponseBuilder.failedMessage("Must have the correct url to reset password.");
		}
		return BucciResponseBuilder.successfulResponseMessage("Reset your password.",new Boolean(true));
	}
	
	@RequestMapping(value="reset_password", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<Boolean> resetPassword(@RequestBody LoginInfo info) {
		User user = userService.findOne(info.email);
		if(user == null || info.password == null) {
			return BucciResponseBuilder.failedMessage("Must have the correct url to reset password.");
		}
		user.setPasswordAndEncrypt(info.password);
		userService.save(user);
		if(info.sendMail){
			try {
				mailManager.sendResetConfirmation(info.password);
			} catch (MessagingException e) {
				return BucciResponseBuilder.failedMessage("The email server is down, wait some time and try again.");
			}
		}
		return BucciResponseBuilder.successfulResponseMessage("Your password has been reset.",new Boolean(true));
	}
	
	@RequestMapping(value="reset_password_nomail", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> resetPasswordNomail(@RequestBody CurrentToNewForm form, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}

		User user;
		try {
			user = userService.confirmAndSavePassword(form, loggedUser);
			session.setAttribute(constants.getSession(), user);
			try {
				mailManager.sendResetConfirmation(user.getEmail());
			} catch (MessagingException e) {
				return BucciResponseBuilder.failedMessage("The email server is down, wait some time and try again.");
			}
			
			return BucciResponseBuilder.successfulResponseMessage("Your password has been reset.",user);
			
		} catch (BucciException e1) {
			return BucciResponseBuilder.failedMessage(e1.getErrMessage());
		}
	}
	
	
	@RequestMapping(value="go_private", method = RequestMethod.PUT)
	public @ResponseBody BucciResponse<User> becomePrivate(@RequestParam boolean secret, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		User user = userService.goPrivate(loggedUser, secret);
		session.setAttribute(constants.getSession(), user);
		String message;
		if(secret) {
			message = "You are now private";
		} else {
			message = "You are now public";
		}
		
		return BucciResponseBuilder.successfulResponseMessage(message, user);
	}
	@RequestMapping(value="follow_artist", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Artist>> followArtist(@RequestParam int artistId, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		List<Artist> artist = userService.followArtist(artistId, loggedUser.getEmail());
		return BucciResponseBuilder.successfulResponse(artist);
	}
	
	@RequestMapping(value="unfollow_artist", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Artist>> unfollowArtist(@RequestParam int artistId, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		List<Artist> artist = userService.unfollowArtist(artistId, loggedUser.getEmail());
		return BucciResponseBuilder.successfulResponse(artist);
	}
	
	@RequestMapping(value="payment_info", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Payment>> getPaymentInfo(HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else if(!BucciPrivilege.isPremium(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getGeneralAccessDeniedMsg());
		}
		
		List<Payment> payments = userService.getPayments((PremiumUser) loggedUser);
		return BucciResponseBuilder.successfulResponse(payments);
	}
	
	@RequestMapping(value="submit_ticket", method = RequestMethod.POST)
	public @ResponseBody void submitTicket(@RequestBody SupportTicket supportTicket, HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return;
		}
		
		userService.saveTicket(supportTicket, loggedUser.getEmail());
	}
	
	
	
	
	@RequestMapping(value="cancel_subscription", method = RequestMethod.PUT)
	public @ResponseBody BucciResponse<User> cancelSubscription(HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else if(!BucciPrivilege.isPremium(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getGeneralAccessDeniedMsg());
		}
		
		PremiumUser user = userService.cancelPremium((PremiumUser) loggedUser);
		session.setAttribute(constants.getSession(), user);
		
		
		String nextBillingDate = new SimpleDateFormat("MMM dd, yyyy").format(user.getNextBillingDate());
		return BucciResponseBuilder.successfulResponseMessage("You are now bucci basic, final charge date is on " + nextBillingDate
				, user);
	}
	
	@RequestMapping(value="activate_subscription", method = RequestMethod.PUT)
	public @ResponseBody BucciResponse<User> activateSubscription(HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		
		try {
			User user = userService.reActivateSubscription(loggedUser);
			session.setAttribute(constants.getSession(), user);
			return BucciResponseBuilder.successfulResponseMessage("You are back to being bucci premium", user);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="change_billing_info", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> changeBillingInfo(@RequestBody BillingInfo billing,HttpSession session) {
		User loggedUser = (User) session.getAttribute(constants.getSession());
		if(loggedUser == null) {
			return BucciResponseBuilder.failedMessage(constants.getNotLoggedInMsg());
		}
		else if(!BucciPrivilege.isPremium(loggedUser)) {
			return BucciResponseBuilder.failedMessage(constants.getGeneralAccessDeniedMsg());
		}
		
		try {
			User user = userService.changeBillingInfo(billing, (PremiumUser) loggedUser);
			session.setAttribute(constants.getSession(), user);
			return BucciResponseBuilder.successfulResponseMessage("Billing information successfully changed", user);
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
	}
	
	@RequestMapping(value="charge_premium_users", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<PremiumUser>> changeBillingInfo() {
		List<PremiumUser> user = userService.chargeUserForPremium();
		return BucciResponseBuilder.successfulResponseMessage("The users were charged", user);
	}
}
