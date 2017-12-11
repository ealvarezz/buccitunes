package com.buccitunes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buccitunes.constants.PaymentType;
import com.buccitunes.constants.Tier;
import com.buccitunes.constants.UserRole;
import com.buccitunes.dao.*;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.miscellaneous.MailManager;
import com.buccitunes.model.AdminUser;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistActivity;
import com.buccitunes.model.ArtistMonthlyStat;
import com.buccitunes.model.ArtistTransaction;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Concert;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedConcert;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;
import com.buccitunes.model.SongPlays;



@Service
@Transactional
public class AdminService {
	
	@Autowired
	private BucciConstants constants;
	
	private final AdminUserRepository adminUserRepository;
	private final AlbumRepository albumRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	private final ConcertRepository concertRepository;
	private final RequestedAlbumRepository requestedAlbumRepository;
	private final RequestedSongRepository requestedSongRepository;
	private final RequestedArtistRepository requestedArtistRepository;
	private final RequestedConcertRepository requestedConcertRepository;
	private final ArtistUserRepository artistUserRepository;
	private final ArtistTransactionRepository artistTransactionRepository;
	private final UserRepository userRepository;
	private final SongPlaysRepository songPlaysRepository;
	private final PremiumUserRepository premiumUserRepository;
	private final ArtistMonthlyStatRepository artistMonthlyStatRepository;
	private final ArtistActivityRepository artistActivityRepository;
	
	public AdminService(AdminUserRepository adminUserRepository, AlbumRepository albumRepository,
			SongRepository songRepository, ArtistRepository artistRepository, ConcertRepository concertRepository,
			RequestedAlbumRepository requestedAlbumRepository, RequestedSongRepository requestedSongRepository,
			RequestedArtistRepository requestedArtistRepository, RequestedConcertRepository requestedConcertRepository, 
			ArtistUserRepository artistUserRepository, UserRepository userRepository, SongPlaysRepository songPlaysRepository,
			ArtistTransactionRepository artistTransactionRepository, PremiumUserRepository premiumUserRepository,
			ArtistMonthlyStatRepository artistMonthlyStatRepository, ArtistActivityRepository artistActivityRepository) {
		this.adminUserRepository = adminUserRepository;
		this.albumRepository = albumRepository;
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
		this.concertRepository = concertRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
		this.requestedSongRepository = requestedSongRepository;
		this.requestedArtistRepository = requestedArtistRepository;
		this.requestedConcertRepository = requestedConcertRepository;
		this.artistUserRepository = artistUserRepository;
		this.userRepository = userRepository;
		this.songPlaysRepository = songPlaysRepository;
		this.artistTransactionRepository = artistTransactionRepository;
		this.premiumUserRepository = premiumUserRepository;
		this.artistMonthlyStatRepository = artistMonthlyStatRepository;
		this.artistActivityRepository = artistActivityRepository;
	}
	
	public Artist addNewArtist(Artist artist) throws BucciException {
		artist = artistRepository.save(artist);
		return artist;
	}
	
	public ArtistUser adminApproveArtist(RequestedArtist requestedArtist) throws BucciException {
		requestedArtist = requestedArtistRepository.findOne(requestedArtist.getId());
		User requestedUser = userRepository.findOne(requestedArtist.getRequester().getEmail());
		if(requestedUser == null) {
			throw new BucciException(constants.getArtistNotFoundMsg());
		}
		Artist artist = new Artist(requestedArtist);
		artist = artistRepository.save(artist);
		
		requestedUser.setRole(UserRole.ARTIST);
		artistUserRepository.upgradeToArtist(requestedUser.getEmail(), artist.getId());
		requestedArtistRepository.delete(requestedArtist);
		
		ArtistUser artUser = new ArtistUser(requestedUser, artist);
		return artUser;
	}
	

	
	public Song addSongToAlbum(Song song) throws BucciException{
		if(song.getAlbum() == null) {
			throw new BucciException(constants.getAlbumNotFoundMsg());
		}
		Album album = albumRepository.findOne(song.getAlbum().getId());
		if(album == null) {
			throw new BucciException(constants.getAlbumNotFoundMsg());
		}
		song.setAlbum(album);
		songRepository.save(song);
		return song;
	}
	
	public Album addAlbum(Album albumToAdd) throws BucciException {
		Artist artist = artistRepository.findOne(albumToAdd.getPrimaryArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(albumToAdd.getPrimaryArtist().getName());
			if(artist == null) {
				throw new BucciException(constants.getArtistNotFoundMsg());
			}
		}
		albumToAdd.setPrimaryArtist(artist);
		Album album = albumRepository.save(albumToAdd);
		if(albumToAdd.getArtwork() != null) {
			try {
				String artworkPath = FileManager.saveAlbumArtwork(albumToAdd.getArtwork(), album.getId());
				album.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException(constants.getStorageErrorMsg());
			}
		}
		return album;
	}
	
	public Album adminApproveAlbum(RequestedAlbum requestedAlbum) throws BucciException {
		Album newAlbum;
		List<RequestedSong> songsToApprove = requestedAlbum.getSongs();
		requestedAlbum = requestedAlbumRepository.findOne(requestedAlbum.getId());
		if(requestedAlbum == null) {
			throw new BucciException(constants.getAlbumNotFoundMsg());
		}
			
		boolean approveAllSongs;
		if(songsToApprove == null) {
			approveAllSongs = true;
		} else {
			approveAllSongs = false;
		}
		
		newAlbum = new Album(requestedAlbum, approveAllSongs);
		newAlbum = albumRepository.save(newAlbum);
		
		ArtistActivity activity = new ArtistActivity(requestedAlbum.getPrimaryArtist(), new Date());
		activity.setFeed(requestedAlbum.getPrimaryArtist().getName()+" added a new album: "+requestedAlbum.getTitle());
		artistActivityRepository.save(activity);
		
		if(requestedAlbum.getArtworkPath() != null) {
			try {				
				String artworkPath = FileManager.moveRequestedArtworkToAlbum(requestedAlbum.getArtworkPath(), newAlbum.getId());
				newAlbum.setArtworkPath(artworkPath);
			} catch (IOException  | BucciException e) {}
		}
		if(songsToApprove != null) {
			List<Song> songsToAlbum = new ArrayList<Song>(songsToApprove.size());
			for (RequestedSong approvedSong : songsToApprove) {
				RequestedSong requestedSong = requestedSongRepository.findOne(approvedSong.getId());	
				if(requestedSong != null) {
					requestedSong.setApproved(approvedSong.isApproved());
					Song newSong = handleApprovedSongOfAlbum(requestedSong, newAlbum, newAlbum.getPrimaryArtist());
					if(newSong != null) {
						songsToAlbum.add(newSong);
					}
				}
			}
			newAlbum.setSongs(songsToAlbum);
		}
		try {
			FileManager.removeRequestedAlbumResources(requestedAlbum);
		} catch (IOException e) {}		
		requestedAlbumRepository.delete(requestedAlbum);
		return newAlbum;
	}
	
	private Song handleApprovedSongOfAlbum(RequestedSong requestedSong, Album album, Artist artist){
		if(requestedSong.isApproved()) {
			Song newSong = new Song(requestedSong,album, artist);
			newSong = songRepository.save(newSong);
			if(requestedSong.getAudioPath() != null) {
				try {
					String audioPath = FileManager.moveRequestedAudio(requestedSong.getAudioPath(), newSong.getId());
					newSong.setAudioPath(audioPath);
				} catch (IOException | BucciException e) {
					System.out.println(constants.getStorageErrorMsg());
				}
			}
			return newSong;
		} else {
			try {
				FileManager.removeRequestedSongResources(requestedSong);
			} catch (IOException e ) {
				System.out.println(constants.getStorageErrorMsg());
			}
			return null;
		}
	}
	
	public Concert adminApproveConcert(RequestedConcert requested) throws BucciException{
		Concert newConcert;
		requested = requestedConcertRepository.findOne(requested.getId());
		if(requested == null) {
			throw new BucciException(constants.getConcertNotFoundMsg());
		}else{
			Artist owner = artistRepository.findOne(requested.getRequester().getArtist().getId()); 
			newConcert = new Concert(requested);
			newConcert.setMainStar(owner);
			
			ArtistActivity activity = new ArtistActivity(requested.getFeaturedArtists().get(0), new Date());
			activity.setFeed(requested.getFeaturedArtists().get(0).getName()+" added a new concert: "+requested.getName());
			artistActivityRepository.save(activity);
		}
		newConcert = concertRepository.save(newConcert);
		requestedConcertRepository.delete(requested);
		return newConcert;
	}
	
	public Song adminApproveSong(RequestedSong requestedSong) throws BucciException{
		requestedSong = requestedSongRepository.findOne(requestedSong.getId());
		if(requestedSong == null) {
			throw new BucciException(constants.getSongNotFoundMsg());
		}
		Artist artist = artistRepository.findOne(requestedSong.getArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(requestedSong.getArtist().getName());
			if(artist == null) {
				throw new BucciException(constants.getArtistNotFoundMsg());
			}
		}
		Song song = songRepository.save(new Song(requestedSong));
		
		ArtistActivity activity = new ArtistActivity(requestedSong.getArtist(), new Date());
		activity.setFeed(requestedSong.getArtist().getName()+" added a new album: "+requestedSong.getName());
		artistActivityRepository.save(activity);
		
		if(requestedSong.getPicturePath() != null) {
			try {				
				String artworkPath = FileManager.moveRequestedArtworkToSong(requestedSong.getPicturePath(), song.getId());
				song.setPicturePath(artworkPath);
			} catch (IOException e) {
				throw new BucciException(constants.getStorageErrorMsg());
			}
		}
		try {
			FileManager.removeRequestedSongResources(requestedSong);
		} catch (IOException e) {
			throw new BucciException(constants.getStorageErrorMsg());
		}
		requestedSongRepository.delete(requestedSong);
		return song;
	}
	
	public List<SongPlays> getSongPLays(int artist_id) {	
		return songPlaysRepository.getCurrentSongPlaysByArtist(artist_id);
	}
	
	public double payRoyalties() {
		double total = 0;
		for(ArtistUser artist: artistUserRepository.findAll()){
			int songPlays = songPlaysRepository.getCurrentSongPlaysByArtist(artist.getArtist().getId()).size();
			if(songPlays > 0) {
				total += songPlays * constants.getRoyaltyPrice();
				ArtistTransaction transaction  = new ArtistTransaction();
				transaction.setAmount(songPlays * constants.getRoyaltyPrice());
				transaction.setArtist(artist.getArtist());
				transaction.setDate(new Date());
				transaction.setPaymentType(PaymentType.ROYALTY_PAYMENT); // Change this to transaction type instead later
				artistTransactionRepository.save(transaction);
			}
		}
		return total;
	}
	
	public void payRoyaltiesByCaching() {
		
		List<ArtistMonthlyStat> monthlyStats = artistMonthlyStatRepository.getLastMonthStats();
		for(ArtistMonthlyStat stat: monthlyStats) {
			ArtistTransaction transaction  = new ArtistTransaction();
			transaction.setAmount(stat.getRevenue());
			transaction.setArtist(stat.getId().getArtist());
			transaction.setDate(new Date());
			transaction.setPaymentType(PaymentType.ROYALTY_PAYMENT); 
			artistTransactionRepository.save(transaction);
		}
	}
	
	public RequestedAlbum getRequestedAlbum(int id) throws BucciException {
		RequestedAlbum album = requestedAlbumRepository.findOne(id);
		if(album == null) {
			throw new BucciException(constants.getAlbumNotFoundMsg());
		}
		album.getSongs().size();
		album.getPrimaryArtist();
		album.getRequester();
		
		return album;
	}
	
	public List<RequestedAlbum> getRequestedAlbums() {
		List<RequestedAlbum> result = new ArrayList<>();
		for(RequestedAlbum requested: requestedAlbumRepository.findAll()) result.add(requested);
		return result;
	}
	
	public void removeRequestedArtist(RequestedArtist requested) throws BucciException {
		requested = requestedArtistRepository.findOne(requested.getId());
		if(requested == null) {
			throw new BucciException(constants.getArtistNotFoundMsg());
		}
		
		requestedArtistRepository.delete(requested);
	}
	
	public void removeRequestedAlbum(RequestedAlbum album) throws BucciException {
		album = requestedAlbumRepository.findOne(album.getId());
		if(album == null) {
			throw new BucciException(constants.getAlbumNotFoundMsg());
		}
		
		try {
			FileManager.removeRequestedAlbumResources(album);
		} catch (IOException e) {
			throw new BucciException(constants.getStorageErrorMsg());
		}
		
		requestedAlbumRepository.delete(album);
	}
	
	public void removeRequestedConcert(RequestedConcert concert) throws BucciException {
		concert = requestedConcertRepository.findOne(concert.getId());
		if(concert == null) {
			throw new BucciException(constants.getConcertNotFoundMsg());
		}
		requestedConcertRepository.delete(concert);
	}
	
	public void removeRequestedSong(RequestedSong song) throws BucciException {
		song = requestedSongRepository.findOne(song.getId());
		if(song == null) {
			throw new BucciException(constants.getSongNotFoundMsg());
		}
		try {
			FileManager.removeRequestedSongResources(song);
		} catch (IOException e) {
			throw new BucciException(constants.getStorageErrorMsg());
		}
		requestedSongRepository.delete(song);
	}
	
	public void chargeUsers() {
		List<PremiumUser> pUsers = premiumUserRepository.getNeededUsersToPay();
		for(PremiumUser user : pUsers) {
			user.makePayment(constants.getMonthlyPremiumPrice());
		}
	}
	
	public AdminUser createAdminUser(AdminUser user) throws BucciException {
		AdminUser existingAdmin = adminUserRepository.findOne(user.getEmail());
		if(existingAdmin != null) {
			throw new BucciException("Email already being used");
		}
		user.encryptPassword();
		user = adminUserRepository.save(user);
		return user;
	}
	
	public RequestedConcert getRequestedConcert(int id) {
		
		return requestedConcertRepository.findOne(id);
	}
	
}
