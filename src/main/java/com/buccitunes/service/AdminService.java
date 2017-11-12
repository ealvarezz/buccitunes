package com.buccitunes.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.*;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;


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
	
	public AdminService(AdminUserRepository adminUserRepository, AlbumRepository albumRepository,
			SongRepository songRepository, ArtistRepository artistRepository, ConcertRepository concertRepository,
			RequestedAlbumRepository requestedAlbumRepository, RequestedSongRepository requestedSongRepository,
			RequestedArtistRepository requestedArtistRepository,
			RequestedConcertRepository requestedConcertRepository, ArtistUserRepository artistUserRepository) {
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
	}
	
	public Artist adminApproveArtist(RequestedArtist requestedArtist) {
		
		//Used to make sure the requestedAlbum information is up to date
		requestedArtist = requestedArtistRepository.findOne(requestedArtist.getId());
		
		
		ArtistUser artistUser = new ArtistUser(requestedArtist);
	
		
		//The songs may delete itself
		//requestedArtistRepository.delete(requestedArtist);
		
		artistUser = artistUserRepository.save(artistUser);
		
		return artistUser.getArtist();
	}
	
	public Album adminApproveAlbum(RequestedAlbum requestedAlbum) {
		
		//Used to make sure the requestedAlbum information is up to date
		//requestedAlbum = requestedAlbumRepository.findOne(requestedAlbum.getId());
		
		
		
		Album album = new Album(requestedAlbum);
		
		//The songs may delete itself
		//requestedAlbumRepository.delete(requestedAlbum);
		
		album = albumRepository.save(album);
		
		return album;
	}
	
	
	
	
	
	
	
	
	
	
	
}
