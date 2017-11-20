package com.buccitunes.service;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PlaylistRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.SongPlaysRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.Song;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.User;

@Service
@Transactional
public class MusicCollectionService {
	
	boolean alter = true; // just for testing purposes, must be removed during production
	private final AlbumRepository albumRepository;
	private final PlaylistRepository playlistRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	private final SongPlaysRepository songPlaysRepository;
	private final UserRepository userRepository;
	
	public MusicCollectionService(AlbumRepository albumRepository, PlaylistRepository playlistRepository,
			SongRepository songRepository, ArtistRepository artistRepository, SongPlaysRepository songPlaysRepository,
			UserRepository userRepository) {
		this.albumRepository = albumRepository;
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
		this.songPlaysRepository = songPlaysRepository;
		this.userRepository = userRepository;
	}
	
	public List<Album> getNewReleasesByCurrentMonth() {
		PageRequest pageRequest = new PageRequest(BucciConstants.PageRequest.START, BucciConstants.Album.LIMITNEWRELASES);
		return albumRepository.getNewReleasesOfMonth(pageRequest);
	}
	
	
	public List<Album> getTopAlbumsByWeek() {
		PageRequest pageRequest = new PageRequest(BucciConstants.PageRequest.START, BucciConstants.Album.LIMITNEWRELASES,
				Sort.Direction.DESC, "numPlays");
		return albumRepository.topAlbumsOfTheWeek(pageRequest);
	}
	
	public List<Playlist> getTopPlaylist() {
		PageRequest pageRequest = new PageRequest(BucciConstants.PageRequest.START, BucciConstants.Album.LIMITNEWRELASES,
				Sort.Direction.DESC, "stats.total_plays");
		return playlistRepository.getTopPlaylistOfAllTime();
	}
	
	public Album getAlbum(int albumId) {
		
	       Album album = albumRepository.findOne(albumId);
	       album.getSongs().size();
	       return album;
	}
	
	public Playlist getPlaylist(int playlistId) {
		
	       Playlist playlist = playlistRepository.findOne(playlistId);
	       playlist.getSongs().size();
	       return playlist;
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
	
	public Song PlaySong(String userId, int songId) throws ParseException {
		
		User user = userRepository.findOne(userId);
		Song song = songRepository.findOne(songId);
		//song.getLyrics().getLyric().length();
		
		SongPlays songPlay = new SongPlays(user, song);
		
		if(alter) {
			// This will be deleted, we should only store new Date() for the current date
			// this is just for testing
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			songPlay.setDatePlayed(format.parse("2017-01-13"));
			alter = !alter;
		}else {
			
			songPlay.setDatePlayed(new Date());
			alter = !alter;
		}
		
		songPlaysRepository.save(songPlay);
		
		return song;
	}
	
	public List<Song> getTopSongs(){
		
		return songRepository.getCurrentTopSongs();
	}
	
	public List<Album> getTopAlbumsByGenre(int genreId) {
		
		PageRequest pageRequest = new PageRequest(BucciConstants.PageRequest.START, BucciConstants.Album.LIMITNEWRELASES,
				Sort.Direction.DESC, "stats.monthly_plays");
		return albumRepository.topAlbumsByGenre(genreId,pageRequest);
	}
	

	
	/*

	public List<Playlist> getTopPlaylistByGenre(int genreId) {
		
		return playlistRepository.topPlaylistsByGenre(genreId);
	}

	
	public List<Song> getTopSongsByArtist(int artistId) {
		
		return songRepository.topSongsByArtist(artistId);
	}

	*/

}
