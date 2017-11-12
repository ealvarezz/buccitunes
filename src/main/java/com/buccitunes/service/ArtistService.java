package com.buccitunes.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.RequestedAlbumRepository;
import com.buccitunes.model.Artist;
import com.buccitunes.model.User;

@Service
@Transactional
public class ArtistService {
	
private final ArtistRepository artistRepository;
private final RequestedAlbumRepository requestedAlbumRepository;
	
	public ArtistService(ArtistRepository artistRepository, RequestedAlbumRepository requestedAlbumRepository) {
		
		this.artistRepository = artistRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
	}
	
	public List<Artist> findAll(){
		
		List<Artist> result = new ArrayList<>();
		for(Artist artist: artistRepository.findAll()) result.add(artist);
			

		return result;
	}
	
	public void save(Artist artist) {
		
		
		artistRepository.save(artist);
	}
	
	public void remove(Integer id) {
		
		
		artistRepository.delete(id);
	}
}
