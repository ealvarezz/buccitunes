package com.buccitunes.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buccitunes.constants.UserRole;
import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistActivityRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.BillingInfoRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PaymentRepository;
import com.buccitunes.dao.PlaylistRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.SongPlaysRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.dao.SupportTicketRepository;
import com.buccitunes.dao.UserActivityRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.jsonmodel.CurrentToNewForm;
import com.buccitunes.jsonmodel.SearchResults;
import com.buccitunes.jsonmodel.SignupFormInfo;
import com.buccitunes.jsonmodel.UserPageInfo;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.BucciResponseBuilder;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.AdminUser;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistActivity;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.CreditCompany;
import com.buccitunes.model.MusicCollection;
import com.buccitunes.model.Payment;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.Song;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.SupportTicket;
import com.buccitunes.model.User;
import com.buccitunes.model.UserActivity;

@Service
@Transactional
public class UserService  {
	
	@Autowired
	private BucciConstants constants;
	
	private final UserRepository userRepository;
	private final AlbumRepository albumRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	private final PremiumUserRepository premiumUserRepository;
	private final PlaylistRepository playlistRepository;
	private final CreditCompanyRepository creditCompanyRepository;
	private final BillingInfoRepository billingInfoRepository;
	private final SupportTicketRepository supportTicketRepository; 
	private final PaymentRepository paymentRepository;
	private final SongPlaysRepository songPlaysRepository;
	private final UserActivityRepository userActivityRepository;

	
	
	public UserService(UserRepository userRepository, PremiumUserRepository premiumUserRepository, 
			CreditCompanyRepository creditCompanyRepository, BillingInfoRepository billingInfoRepository, 
			AlbumRepository albumRepository, SongRepository songRepository, PlaylistRepository playlistRepository,
			ArtistRepository artistRepository, SupportTicketRepository supportTicketRepository,
			PaymentRepository paymentRepository, SongPlaysRepository songPlaysRepository,
			UserActivityRepository userActivityRepository) {
		
		this.userRepository = userRepository;
		this.premiumUserRepository = premiumUserRepository;
		this.creditCompanyRepository = creditCompanyRepository;
		this.billingInfoRepository = billingInfoRepository;
		this.albumRepository = albumRepository;
		this.songRepository = songRepository;
		this.playlistRepository = playlistRepository;
		this.artistRepository = artistRepository;
		this.supportTicketRepository = supportTicketRepository;
		this.paymentRepository = paymentRepository;
		this.songPlaysRepository = songPlaysRepository;
		this.userActivityRepository = userActivityRepository;
	}
	
	public List<User> findAll(){
		
		List<User> result = new ArrayList<>();
		for(User user: userRepository.findAll()) result.add(user);
			
		return result;
	}
	
	public void save(User user) {
		
		userRepository.save(user);
	}
	
	public void remove(String email) {
		
		userRepository.delete(email);
	}
	
	public void deleteUser(User user, String password) throws BucciException {
		user = userRepository.findOne(user.getEmail());
		
		if(user == null || !user.passwordIsCorrect(password)) {
			throw new BucciException("Invalid Login Information");	
		}
		
		List<SongPlays> songs = songPlaysRepository.findByUser(user);
		for(SongPlays playedSong : songs) {
			playedSong.setUser(null);
		}
		
		String emailId = user.getEmail();
		if(BucciPrivilege.isPremium(user)) {
			premiumUserRepository.delete(emailId);
		} else {
			userRepository.delete(emailId);
		
			//Case is used if the premium user downgraded to a basic user
			if(premiumUserRepository.exists(emailId) ) {
				System.out.println("Deleting Premium stuff");
				premiumUserRepository.delete(emailId);
			}
		}
		
		
	}
	
	public User follow(String follower, String followed) throws BucciException {
		
		User followingUser = userRepository.findOne(follower);
		User followedUser = userRepository.findOne(followed);
		
		if(followingUser == null || followedUser == null) {
			throw new BucciException("User not found");
		}
		
		followingUser.getFollowing().add(followedUser);
		
		UserActivity activity = new UserActivity(followingUser, new Date());
		activity.setFeed(followingUser.getEmail()+" is now following: "+followedUser.getEmail());
		userActivityRepository.save(activity);
		
		return followedUser;
	}
	
	public void unfollow(String follower, String followed) throws BucciException {
		
		User followingUser = userRepository.findOne(follower);
		User followedUser = userRepository.findOne(followed);
		
		if(followingUser == null || followedUser == null) {
			throw new BucciException("User not found");
		}
		
		followingUser.getFollowing().remove(followedUser);
	}
	
	public User findOne(String email){
		
		return userRepository.findOne(email);
	}
	
	
	public List<User> getFollowing(String email) throws BucciException{
		
		User user = userRepository.findOne(email);
		if(user == null) {
			throw new BucciException("User not found"); 
		}
		
		return user.getFollowing();
	}
	
	public List<User> getFollowers(String email) throws BucciException{
		
		User user = userRepository.findOne(email);
		if(user == null) {
			throw new BucciException("User not found"); 
		}
		
		return user.getFollowers();
	}
	
	public List<User> findByName(String name) {
		
		return userRepository.findByName(name);
	}
	
	public void updateName(String email, String name) {
		
		User user = userRepository.findOne(email);
		
		user.setName(name);
		//userRepository.save(user);
	}
	
	public User signup(SignupFormInfo signupInfo) throws BucciException {
				
		User user = userRepository.findOne(signupInfo.userInfo.getEmail());
		if(user != null) {
			throw new BucciException("Email already being used");
		}
		user = signupInfo.userInfo;
		
		user.setPasswordAndEncrypt(signupInfo.userInfo.getPassword());
		
		if(signupInfo.artistInfo != null) {
			throw new BucciException("NOT IMPLEMENTED");
		}
		else if(signupInfo.billingInfo != null) {
			String invalidBillingInfo = signupInfo.billingInfo.checkInvalidInfo(); 
			
			//If the billingInfo entered is invalid
			if(!invalidBillingInfo.equals("")) {
				throw new BucciException(invalidBillingInfo);
			}
			
			PremiumUser pUser = new PremiumUser(user,signupInfo.billingInfo);
			pUser.makePayment(constants.getSignupPremiumPrice());
			PremiumUser newUser = premiumUserRepository.save(pUser);
			return newUser;
		} else {
			User newUser = userRepository.save(user);
			return newUser;
		}
	}
	
	public PremiumUser upgradeToPremium(User user, BillingInfo billingInfo) throws BucciException {
		PremiumUser existingPremium = premiumUserRepository.findOne(user.getEmail());
		
		if(existingPremium != null) {
			throw new BucciException("You are already a premium user");
		}
	 
		user = userRepository.findOne(user.getEmail());
		if(user == null) {
			throw new BucciException("Original user not found");
		}
		
		String invalidBillingInfo = billingInfo.checkInvalidInfo();
		if(!invalidBillingInfo.equals("") ) {
			throw new BucciException(invalidBillingInfo);
		}
		
		CreditCompany cardCompany = creditCompanyRepository.findOne(billingInfo.getCreditCardCompany().getId());
		if(cardCompany == null) {
			throw new BucciException("Invalid Billing Infomation");
		}
		
		billingInfo.setCreditCardCompany(cardCompany);
		billingInfo = billingInfoRepository.save(billingInfo);
		
		user.setRole(UserRole.PREMIUM);
		premiumUserRepository.upgradeToPremium(user.getEmail(), billingInfo.getId());		
		PremiumUser pUser = premiumUserRepository.findOne(user.getEmail());
		return pUser;
	}
	
	public List<Playlist> getFollowedPlaylist(String email){
		
		User user = userRepository.findOne(email);
		user.getFollowingPlaylists().size();
		return user.getFollowingPlaylists();
	}
	
	public List<Artist> getFollowedArtist(String email){
		
		User user = userRepository.findOne(email);
		user.getFollowingArtists().size();
		return user.getFollowingArtists();
	}
	
	public List<Album> getSavedAlbums(String email){
		
		User user = userRepository.findOne(email);
		user.getSavedAlbums().size();
		return user.getSavedAlbums();
	}
	
	public void saveAlbum(int albumId, String email) {
		
		User user = userRepository.findOne(email);
		Album album = albumRepository.findOne(albumId);
		user.getSavedAlbums().add(album);

	}
	
	public void followPlaylist(int playlistId, String email) {
		
		User user = userRepository.findOne(email);
		Playlist playlist = playlistRepository.findOne(playlistId);
		user.getFollowingPlaylists().size();
		user.getFollowingPlaylists().add(playlist);
	}
	
	public void unfollowPlaylist(int playlistId, String email) {
		
		User user = userRepository.findOne(email);
		Playlist playlist = playlistRepository.findOne(playlistId);
		user.getFollowingPlaylists().size();
		user.getFollowingPlaylists().remove(playlist);
		
		playlistRepository.delete(playlistId);
	}
	
	public void saveSong(int songId, String email) {
		
		User user = userRepository.findOne(email);
		Song song = songRepository.findOne(songId);
		user.getSavedSongs().add(song);
		
	}
	
	public List<Song> getSavedSongs(String email) {
		
		User user = userRepository.findOne(email);
		user.getSavedSongs().size();
		return user.getSavedSongs();
		
	}
	
	public List<Album> getRecentAlbumsPlayed(String email){
		
		return albumRepository.getRecentlyPlayed(email);
	}
	
	public SearchResults search(String keyword) {
		
		return new SearchResults(
				songRepository.searchSong(keyword),
				artistRepository.searchArtist(keyword),
				albumRepository.searchAlbum(keyword),
				playlistRepository.searchPlaylist(keyword)
				
		);
	}
	
	public List<Playlist> getUserPlaylists(User loggedInUser) throws BucciException{
		User user  = userRepository.findOne(loggedInUser.getEmail());
		
		if (user == null) {
			throw new BucciException("User not found");
		}
		user.getPlaylists().size();
		
		return user.getPlaylists();
		
	}
	
	public UserPageInfo getUserInfo(String id, User loggedUser) throws BucciException {
		User user = userRepository.findOne(id);
		
		if(user == null) {
			throw new BucciException("User not found");
		}
		
		if(loggedUser.getEmail().equals(user.getEmail()) || !user.isInPrivateMode()) {
			List<Album> recentlyPlayedAlbum = albumRepository.getRecentlyPlayed(user.getEmail());
			List<MusicCollection> recentlyPlayed = new ArrayList<MusicCollection>(recentlyPlayedAlbum);
			user.setRecentlyPlayed(recentlyPlayed);
			user.getPlaylists().size();
			user.getFollowers().size();
			user.getFollowing().size();
			user.getFollowingPlaylists().size();
		}
		
		boolean isAFollower = false;
		if(userRepository.isFollowing(loggedUser.getEmail(), user.getEmail())) {
			isAFollower = true;
		}
		
		return new UserPageInfo(user, isAFollower);
	}
	
	public User changeUserInfo(User loggedUser, User changedUser) throws BucciException {
		User user = userRepository.findOne(loggedUser.getEmail());
		
		if(user == null || !user.getEmail().equals(changedUser.getEmail())) {
			throw new BucciException("Emails do not match");
		}
		
		if(changedUser.getAvatar() != null) {
			try {
				String avatarPath = FileManager.saveUserAvatar(changedUser.getAvatar(), changedUser.getEmail());
				changedUser.setAvatarPath(avatarPath);
			} catch (IOException e) {
				throw new BucciException("Unable to save avatar picture");
			}
		}
		
		user.updateUserInfo(changedUser);
		return user;
	}
	
	public List<Artist> getRelatedArtists(int artistId){
		return artistRepository.getRelatedArtist(artistId);
	}
	
	public List<Artist> followArtist(int artistId, String email){
		
		User user = userRepository.findOne(email);
		Artist artist = artistRepository.findOne(artistId);
		user.getFollowingArtists().size();
		user.getFollowingArtists().add(artist);

		UserActivity activity = new UserActivity(user, new Date());
		activity.setFeed(user.getEmail()+" is now following: "+artist.getName());
		userActivityRepository.save(activity);
		
		return user.getFollowingArtists();
		
	}
	
	public List<Artist> unfollowArtist(int artistId, String email){
		
		User user = userRepository.findOne(email);
		Artist artist = artistRepository.findOne(artistId);
		user.getFollowingArtists().size();
		user.getFollowingArtists().remove(artist);
		return user.getFollowingArtists();
		
	}
	
	public User goPrivate(User user, boolean secret) {
		 user = userRepository.findOne(user.getEmail());
		 user.setInPrivateMode(secret);
		 return user;
	}
	
	public void saveTicket(SupportTicket supportTicket, String email) {
		 User user = userRepository.findOne(email);
		 supportTicket.setTicketHolder(user);
		 supportTicketRepository.save(supportTicket);
	}
	
	public User confirmAndSavePassword(CurrentToNewForm form, User user) throws BucciException {
		user = userRepository.findOne(user.getEmail());
		
		if(!user.passwordIsCorrect(form.current) ) {
			throw new BucciException("Invalid Password Information");
		}
		user.setPasswordAndEncrypt(form.toNew);
		
		return user;
	}
	
	public List<Payment> getPayments(PremiumUser user) {
		List<Payment> paymentHistory = paymentRepository.findByPremiumUserOrderByDateDesc(user);
		
		if(paymentHistory.size() > 0) {
			Payment lastPayment = paymentHistory.get(0);
			Date lastPayDate = lastPayment.getDate();
		
			Date nextBillingDate =  getNextBillingDate(lastPayDate);
			lastPayment.setNextBillingDate(nextBillingDate);
			
		}
		
		return paymentHistory;
	}
	
	public PremiumUser cancelPremium(PremiumUser user) {
		user = premiumUserRepository.findOne(user.getEmail());
		
		Date lastPayDate = paymentRepository.findTopByPremiumUserOrderByDateDesc(user).getDate();
		Date nextBillingDate =  getNextBillingDate(lastPayDate);
		
		user.getBillingInfo().setActive(false);
		user.setRole(UserRole.USER);
		user.setNextBillingDate(nextBillingDate);
	
		return user;
	}
	
	public User reActivateSubscription(User loggedUser) throws BucciException {
		PremiumUser pUser = premiumUserRepository.findOne(loggedUser.getEmail());
		if(pUser == null) {
			throw new BucciException("You were never a premium user before!");
		}
		
		
		
		/*
		 * TODO for charging the user if the user hasn't paid in a month
		Date previousBillingDate = paymentRepository.findTopByPremiumUserOrderByDateDesc(pUser).getDate();
		//Used to get
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, -1);
		Date nextBillingDate = cal.getTime();
		
		
		if(previousBillingDate.compareTo(nextBillingDate)){
			
		}
		*/
		
		pUser.getBillingInfo().setActive(true);
		pUser.setRole(UserRole.PREMIUM);
		
		return pUser;
	}
	
	public PremiumUser changeBillingInfo(BillingInfo billing, PremiumUser user) throws BucciException {
		user = premiumUserRepository.findOne(user.getEmail());
		if(user.getBillingInfo().getId() != billing.getId()) {
			throw new BucciException("User does not own this billing information");
		}
		BillingInfo userBilling = user.getBillingInfo();
		userBilling.updateBillingInfo(billing);
		return user;
	}
	
	public List<PremiumUser> chargeUserForPremium() {
		List<PremiumUser> users = premiumUserRepository.getNeededUsersToPay();
		
		for(PremiumUser user : users) {
			boolean isPaid = true;
			//Logic to actually charge users here
			
			if(isPaid) {
				//Payment payment = new Payment(constants.getMonthlyPremiumPrice(),user);
				//paymentRepository.save(payment);
				user.makePayment(constants.getMonthlyPremiumPrice());
			}
		}
		return users;
	}
	
	private Date getNextBillingDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);		
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}
}
