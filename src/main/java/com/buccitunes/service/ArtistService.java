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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.RequestedAlbumRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;

@Service
@Transactional
public class ArtistService {
	
private final ArtistRepository artistRepository;
private final RequestedAlbumRepository requestedAlbumRepository;
private final ArtistUserRepository artistUserRepository;
private final SongRepository songRepository;
	
	public ArtistService(ArtistRepository artistRepository, RequestedAlbumRepository requestedAlbumRepository,
			ArtistUserRepository artistUserRepository,SongRepository songRepository) {
		
		this.artistRepository = artistRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
		this.artistUserRepository = artistUserRepository;
		this.songRepository = songRepository;
	}
	
	public List<Artist> findAll(){
		
		List<Artist> result = new ArrayList<>();
		for(Artist artist: artistRepository.findAll()) result.add(artist);
			

		return result;
	}
	
	public Artist getArtist(int id) throws BucciException {
		Artist artist = artistRepository.findOne(id);
		
		if(artist != null) {
			artist.getAlbums().size();
			return artist;
		} else {
			throw new BucciException("Artist not found");
		}
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
			String avatar = artistUser.getArtist().getAvatar();
			artistUser.getArtist().setAvatar("");
			artistUserRepository.save(artistUser);
			ArtistUser savedArtist = artistUserRepository.findOne(artistUser.getEmail());
			
			if(avatar != null)  {
				String avatarPath = FileManager.saveArtistAlias(avatar, savedArtist.getArtist().getId());
				savedArtist.getArtist().setAvatar(avatarPath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new BucciException("UNABLE TO SAVE ARTWORK");
		}
		
	}
	
	public void remove(Integer id) {
		
		
		artistRepository.delete(id);
	}

	public List<Song> getTopTenSongs(int artistId) {
		PageRequest pageRequest = new PageRequest(BucciConstants.PageRequest.START, BucciConstants.Artist.TOPSONGSLIMIT,
				Sort.Direction.DESC, "stats.total_plays");
		return songRepository.getTopSongsOfAllTimeByArtist(artistId, pageRequest);
		
	}
}
