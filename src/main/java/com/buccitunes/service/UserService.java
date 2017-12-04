package com.buccitunes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.BillingInfoRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PlaylistRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.jsonmodel.SearchResults;
import com.buccitunes.jsonmodel.SignupFormInfo;
import com.buccitunes.jsonmodel.UserPageInfo;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.CreditCompany;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;

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
	
	public UserService(UserRepository userRepository, PremiumUserRepository premiumUserRepository, 
			CreditCompanyRepository creditCompanyRepository, BillingInfoRepository billingInfoRepository, 
			AlbumRepository albumRepository, SongRepository songRepository, PlaylistRepository playlistRepository,
			ArtistRepository artistRepository) {
		
		this.userRepository = userRepository;
		this.premiumUserRepository = premiumUserRepository;
		this.creditCompanyRepository = creditCompanyRepository;
		this.billingInfoRepository = billingInfoRepository;
		this.albumRepository = albumRepository;
		this.songRepository = songRepository;
		this.playlistRepository = playlistRepository;
		this.artistRepository = artistRepository;
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
	
	public User follow(String follower, String followed) throws BucciException {
		
		User followingUser = userRepository.findOne(follower);
		User followedUser = userRepository.findOne(followed);
		
		if(followingUser == null || followedUser == null) {
			throw new BucciException("User not found");
		}
		
		followingUser.getFollowing().add(followedUser);
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
		
		boolean signedForPremium = false;
		if(signupInfo.billingInfo != null) {
			signedForPremium = true;
		}
		
		User user = userRepository.findOne(signupInfo.userInfo.getEmail());
		
		if(user != null) {
			throw new BucciException("Email already being used");
		}
		
		user = signupInfo.userInfo;
		
		user.setPasswordAndEncrypt(signupInfo.userInfo.getPassword());
		
		if(signedForPremium) {
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
		
		if(!user.isInPrivateMode()) {
			user.getRecentlyPlayed().size();
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
}
