package com.buccitunes.service;


import java.io.IOException;

import java.util.Calendar;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PlaylistRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.Song;

@Service
@Transactional
public class MusicCollectionService {
	
	private final AlbumRepository albumRepository;
	private final PlaylistRepository playlistRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	
	public MusicCollectionService(AlbumRepository albumRepository, PlaylistRepository playlistRepository,
			SongRepository songRepository, ArtistRepository artistRepository) {
		this.albumRepository = albumRepository;
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
	}
	
	public List<Album> getNewReleasesByCurrentMonth() {
		
		Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH) + 1;
        
		List<Album> albums = albumRepository.getByReleasesMonth(month);
		return albums;
	}
	
	
	public List<Album> getTopAlbumsByWeek() {
		return null;
	}

	public void saveAlbum(Album album) throws BucciException{
		
			
			String artworkString = album.getArtwork();
			Artist albumOwner = artistRepository.findByName(album.getPrimaryArtist().getName());
			album.setPrimaryArtist(albumOwner);
			for(Song song: album.getSongs()) song.setOwner(albumOwner);
			album.setArtwork("");
			Album returnedAlbum = albumRepository.save(album);
			
			albumOwner.getAlbums().add(album); // suppose to add album by adding to artist's album list
			
			
			if(artworkString != null)  {
				try {
					
					String artworkPath = FileManager.saveArtwork(artworkString, returnedAlbum.getId());
					album.setArtworkPath(artworkPath);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new BucciException("UNABLE TO SAVE ALBUM");
				}
			}
			
			if(album.getReleaseDate() == null) {
				
				album.setReleaseDate(new Date());
			}
			
			album.setDateCreated(new Date());
			
		
	}

}
