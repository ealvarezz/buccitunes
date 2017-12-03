package com.buccitunes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buccitunes.constants.PaymentType;
import com.buccitunes.dao.*;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistTransaction;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;
import com.mysql.jdbc.Constants;
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
	
	public Song addSongToAlbum(Song song) throws BucciException{
		if(song.getAlbum() == null) {
			throw new BucciException("No album specified");
		}
		
		Album album = albumRepository.findOne(song.getAlbum().getId());
		if(album == null) {
			throw new BucciException("Album does not exist");
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
				throw new BucciException("Artist not enter");
			}
		}
		albumToAdd.setPrimaryArtist(artist);
		
		Album album = albumRepository.save(albumToAdd);
		
		if(albumToAdd.getArtwork() != null) {
			try {
				String artworkPath = FileManager.saveAlbumArtwork(albumToAdd.getArtwork(), album.getId());
				album.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		return album;
	}
	
	public Album adminApproveAlbum(RequestedAlbum requestedAlbum) throws BucciException {
		
		Album newAlbum;
		
		requestedAlbum = requestedAlbumRepository.findOne(requestedAlbum.getId());
		if(requestedAlbum == null) {
			throw new BucciException("Requested album does not exist");
		}
			
		List<RequestedSong> songsToApprove = requestedAlbum.getSongs();
		if(songsToApprove == null) {
			newAlbum = new Album(requestedAlbum, true);
		} else {
			newAlbum = new Album(requestedAlbum, false);
		}
		newAlbum = albumRepository.save(newAlbum);
		
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
					System.out.println("\n===========\n AUDIO: " + requestedSong.getAudioPath() + " could not save!!!==========\n");
				}
			}
			
			return newSong;
			
		} else {
			
			try {
				FileManager.removeRequestedSongResources(requestedSong);
			} catch (IOException e ) {
				System.out.println("\n========\n Could not remove requested '" + requestedSong.getName() + "' song==========\n");
			}
			
			return null;
		}
	}
	
	public Song adminApproveSong(RequestedSong requestedSong) throws BucciException{
		requestedSong = requestedSongRepository.findOne(requestedSong.getId());
		if(requestedSong == null) {
			throw new BucciException("Requested album does not exist");
		}
		
		Artist artist = artistRepository.findOne(requestedSong.getArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(requestedSong.getArtist().getName());
			if(artist == null) {
				throw new BucciException("Artist not found");
			}
		}
		
		Song song = songRepository.save(new Song(requestedSong));
		
		if(requestedSong.getPicturePath() != null) {
			try {				
				String artworkPath = FileManager.moveRequestedArtworkToSong(requestedSong.getPicturePath(), song.getId());
				song.setPicturePath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("Unable to add artwork");
			}
		}
		
		try {
			FileManager.removeRequestedSongResources(requestedSong);
		} catch (IOException e) {
			throw new BucciException("Unable to remove artwork");
		}
		
		requestedSongRepository.delete(requestedSong);
		return song;
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
				
				total += songPlays * constants.getRoyaltyPrice();
				ArtistTransaction transaction  = new ArtistTransaction();
				transaction.setAmount(songPlays * constants.getRoyaltyPrice());
				transaction.setArtistUser(artist);
				transaction.setDate(new Date());
				transaction.setPaymentType(PaymentType.ROYALTY_PAYMENT); // Change this to transaction type instead later
				artistTransactionRepository.save(transaction);
			}
			
		}
		
		return total;
	}
	
	public RequestedAlbum getRequestedAlbum(int id) throws BucciException {
		RequestedAlbum album = requestedAlbumRepository.findOne(id);
		if(album == null) {
			throw new BucciException("Requested album not found");
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
	
	public void removeRequestedAlbum(RequestedAlbum album) throws BucciException {
		album = requestedAlbumRepository.findOne(album.getId());
		if(album == null) {
			throw new BucciException("Album not found");
		}
		
		try {
			FileManager.removeRequestedAlbumResources(album);
		} catch (IOException e) {
			throw new BucciException("Failed to remove album resources, try again.");
		}
		
		requestedAlbumRepository.delete(album);
	}
	
	public void removeRequestedSong(RequestedSong song) throws BucciException {
		song = requestedSongRepository.findOne(song.getId());
		if(song == null) {
			throw new BucciException("Song not found");
		}
		
		try {
			FileManager.removeRequestedSongResources(song);
		} catch (IOException e) {
			throw new BucciException("Failed to remove song resources, try again.");
		}
		
		requestedSongRepository.delete(song);
	}
	
	public List<PremiumUser> chargeUsers() {
		return null;
	}
}
