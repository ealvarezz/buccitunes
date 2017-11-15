package com.buccitunes.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.RequestedAlbumRepository;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.User;

@Service
@Transactional
public class ArtistService {
	
private final ArtistRepository artistRepository;
private final RequestedAlbumRepository requestedAlbumRepository;
private final ArtistUserRepository artistUserRepository;
	
	public ArtistService(ArtistRepository artistRepository, RequestedAlbumRepository requestedAlbumRepository, ArtistUserRepository artistUserRepository) {
		
		this.artistRepository = artistRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
		this.artistUserRepository = artistUserRepository;
	}
	
	public List<Artist> findAll(){
		
		List<Artist> result = new ArrayList<>();
		for(Artist artist: artistRepository.findAll()) result.add(artist);
			

		return result;
	}
	
	public Artist getArtistByName(String name) throws BucciException {
		Artist artist = artistRepository.findByName(name);
		
		if(artist != null) {
			return artist;
		} else {
			throw new BucciException("Artist not found");
		}
	}
	
	public void save(Artist artist) {
		
		
		
		
		artistRepository.save(artist);
		
	}
	
	public void saveArtistUser(ArtistUser artistUser) throws BucciException {
		
		try{
			
			if(artistUser.getArtist().getAvatar() != null)  {
				
				String avatarPath = FileManager.saveArtwork(artistUser.getArtist().getAvatar(), artistUser.getArtist().getId());
				
				artistUser.getArtist().setAvatar(avatarPath);
			}
				
			artistUserRepository.save(artistUser);
			
				
				
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new BucciException("UNABLE TO SAVE ARTWORK");
		}
		
	}
	
	public void remove(Integer id) {
		
		
		artistRepository.delete(id);
	}
}
