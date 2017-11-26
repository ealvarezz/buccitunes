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
import com.buccitunes.dao.RequestedSongRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.Tier;
import com.buccitunes.model.User;

@Service
@Transactional
public class ArtistService {
	
private final ArtistRepository artistRepository;
private final RequestedAlbumRepository requestedAlbumRepository;
private final ArtistUserRepository artistUserRepository;
private final SongRepository songRepository;
private final RequestedSongRepository requestedSongRepository;
	
	public ArtistService(ArtistRepository artistRepository, RequestedAlbumRepository requestedAlbumRepository,
			ArtistUserRepository artistUserRepository, SongRepository songRepository, 
			RequestedSongRepository requestedSongRepository) {
		
		this.artistRepository = artistRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
		this.artistUserRepository = artistUserRepository;
		this.songRepository = songRepository;
		this.requestedSongRepository = requestedSongRepository;
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
	
	public ArtistUser saveArtistUser(ArtistUser artistUser) throws BucciException {
		artistUser.encryptPassword();
		try{
			String avatar = artistUser.getArtist().getAvatar();
			artistUser.setTier(Tier.NO_TIER);
			artistUserRepository.save(artistUser);
			ArtistUser savedArtist = artistUserRepository.findOne(artistUser.getEmail());
			
			if(avatar != null)  {
				String avatarPath = FileManager.saveArtistAvatar(avatar, savedArtist.getArtist().getId());
				savedArtist.getArtist().setAvatarPath(avatarPath);
			}
			return savedArtist;
		} catch (IOException e) {
			throw new BucciException("UNABLE TO SAVE ARTWORK");
		}
	}
	
	public void remove(Integer id) {
		artistRepository.delete(id);
	}

	public List<Song> getTopTenSongs(int artistId) {
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.TOP_SONGS_LIMIT,
				Sort.Direction.DESC, BucciConstants.PLAY_COUNT);
		//return songRepository.getTopSongsOfArtist(BucciConstants.TimeAgo.ALL_TIME, artistId, pageRequest);
		return null;
		
	}
	
	public RequestedAlbum requestNewAlbum(RequestedAlbum requested, ArtistUser artistUser) throws BucciException {
		
		String artwork = requested.getArtwork();
		artistUser = artistUserRepository.findOne(artistUser.getEmail());
		requested.setArtistRequester(artistUser);
		//requestedSongRepository.save(requested.getSongs());
		RequestedAlbum requestedAlbum = requestedAlbumRepository.save(requested);
		
		if(artwork != null) {
			try {
				String artworkPath = FileManager.saveRequestedAlbumArtwork(artwork, requestedAlbum.getId());
				requestedAlbum.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		return requestedAlbum;
	}
}
