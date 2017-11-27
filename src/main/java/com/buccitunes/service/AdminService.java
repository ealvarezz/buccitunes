package com.buccitunes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.*;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistTransaction;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PaymentType;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.User;
import com.buccitunes.model.SongPlays;


@Service
@Transactional
public class AdminService {
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
	
	public AdminService(AdminUserRepository adminUserRepository, AlbumRepository albumRepository,
			SongRepository songRepository, ArtistRepository artistRepository, ConcertRepository concertRepository,
			RequestedAlbumRepository requestedAlbumRepository, RequestedSongRepository requestedSongRepository,
			RequestedArtistRepository requestedArtistRepository, RequestedConcertRepository requestedConcertRepository, 
			ArtistUserRepository artistUserRepository, UserRepository userRepository, SongPlaysRepository songPlaysRepository,
			ArtistTransactionRepository artistTransactionRepository) {
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
	}
	
	public Artist addNewArtist(Artist artist) throws BucciException {
		artist = artistRepository.save(artist);
		return artist;
	}
	
	public ArtistUser adminApproveArtist(RequestedArtist requestedArtist) throws BucciException {
		
		//Used to make sure the requestedAlbum information is up to date
		requestedArtist = requestedArtistRepository.findOne(requestedArtist.getId());
		User requestedUser = userRepository.findOne(requestedArtist.getRequester().getEmail());
		
		if(requestedUser == null) {
			throw new BucciException("Invalid User Requested");
		}
		
		Artist artist = new Artist(requestedArtist);
		
		//The songs may delete itself
		//requestedArtistRepository.delete(requestedArtist);
		
		//artistUser = artistUserRepository.save(artistUser);
		
		
		artist = artistRepository.save(artist);
		artistUserRepository.upgradeToArtist(requestedUser.getEmail(), artist.getId());		
		ArtistUser artUser = artistUserRepository.findOne(requestedUser.getEmail());
		
		return artUser;
	}
	
	public Album addAlbum(RequestedAlbum requestedAlbum) throws BucciException {
		
		Artist artist = artistRepository.findOne(requestedAlbum.getPrimaryArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(requestedAlbum.getPrimaryArtist().getName());
			if(artist == null) {
				throw new BucciException("Artist not enter");
			}
		}
		requestedAlbum.setPrimaryArtist(artist);
		
		Album album = albumRepository.save(new Album(requestedAlbum));
		
		if(requestedAlbum.getArtwork() != null) {
			try {
				String artworkPath = FileManager.saveAlbumArtwork(requestedAlbum.getArtwork(), album.getId());
				album.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		return album;
	}
	
	public Album adminApproveAlbum(RequestedAlbum requestedAlbum) throws BucciException {
		
		requestedAlbum = requestedAlbumRepository.findOne(requestedAlbum.getId());
		if(requestedAlbum == null) {
			throw new BucciException("Requested album does not exist");
		}
		
		Artist artist = artistRepository.findOne(requestedAlbum.getPrimaryArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(requestedAlbum.getPrimaryArtist().getName());
			if(artist == null) {
				throw new BucciException("Artist not found");
			}
		}
		
		
		System.out.println("\n=====\nREADY TO SAVE ?\n=====\n");
		 
		Album album = albumRepository.save(new Album(requestedAlbum));
		
		if(requestedAlbum.getArtworkPath() != null) {
			try {				
				String artworkPath = FileManager.moveRequestedArtworkToAlbum(requestedAlbum.getId(), album.getId());
				album.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		
		try {
			FileManager.removeRequestedAlbumResources(requestedAlbum);
		} catch (IOException e) {
			throw new BucciException("UNABLE TO SAVE ARTWORK");
		}
		
		requestedAlbumRepository.delete(requestedAlbum);
		
		return album;
	}
	
	public List<SongPlays> getSongPLays(int artist_id) {
		
		return songPlaysRepository.getCurrentSongPlaysByArtist(artist_id);
	}
	
	/**
	 * This function pays royalties to artists monthly. 
	 * @return Total amount payed.
	 */
	
	public double payRoyalties() {
		
		double total = 0;
		for(ArtistUser artist: artistUserRepository.findAll()){
			
			int songPlays = songPlaysRepository.getCurrentSongPlaysByArtist(artist.getArtist().getId()).size();
			if(songPlays > 0) {
				
				total += songPlays * BucciConstants.ROYALTY_PRICE;
				ArtistTransaction transaction  = new ArtistTransaction();
				transaction.setAmount(songPlays * BucciConstants.ROYALTY_PRICE);
				transaction.setArtistUser(artist);
				transaction.setDate(new Date());
				transaction.setPaymentType(PaymentType.ROYALTY_PAYMENT); // Change this to transaction type instead later
				artistTransactionRepository.save(transaction);
			}
			
		}
		
		return total;
	}
	
	
	public List<RequestedAlbum> getRequestedAlbums() {
				
		List<RequestedAlbum> result = new ArrayList<>();
		for(RequestedAlbum requested: requestedAlbumRepository.findAll()) result.add(requested);
		
		return result;
	}
}
