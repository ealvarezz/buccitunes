package com.buccitunes.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.*;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.User;


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
	private final UserRepository userRepository;
	
	public AdminService(AdminUserRepository adminUserRepository, AlbumRepository albumRepository,
			SongRepository songRepository, ArtistRepository artistRepository, ConcertRepository concertRepository,
			RequestedAlbumRepository requestedAlbumRepository, RequestedSongRepository requestedSongRepository,
			RequestedArtistRepository requestedArtistRepository, RequestedConcertRepository requestedConcertRepository, 
			ArtistUserRepository artistUserRepository, UserRepository userRepository) {
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
	
	public Album adminApproveAlbum(RequestedAlbum requestedAlbum) throws BucciException {
		
		//Used to make sure the requestedAlbum information is up to date
		//requestedAlbum = requestedAlbumRepository.findOne(requestedAlbum.getId());
		Artist artist = artistRepository.findOne(requestedAlbum.getPrimaryArtist().getId());
		if(artist == null) {
			artist = artistRepository.findByName(requestedAlbum.getPrimaryArtist().getName());
			if(artist == null) {
				throw new BucciException("Artist not enter");
			}
		}
		requestedAlbum.setPrimaryArtist(artist);
		
		Album album = albumRepository.save(new Album(requestedAlbum));
		
		//The songs may delete itself
		//requestedAlbumRepository.delete(requestedAlbum);
		
		if(requestedAlbum.getArtwork() != null) {
			try {
				String artwork = FileManager.saveAlbumAlias(requestedAlbum.getArtwork(), album.getId());
				album.setArtwork(artwork);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		return album;
	}
	
}
