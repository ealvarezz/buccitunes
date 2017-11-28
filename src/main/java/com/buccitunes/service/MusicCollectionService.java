package com.buccitunes.service;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.GenreRepository;
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
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Genre;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.Song;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.Tier;
import com.buccitunes.model.User;
import com.buccitunes.resultset.AlbumWithStats;

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
	private final GenreRepository genreRepository;
	private final ArtistUserRepository artistUserRepository;
	
	public MusicCollectionService(AlbumRepository albumRepository, PlaylistRepository playlistRepository,
			SongRepository songRepository, ArtistRepository artistRepository, SongPlaysRepository songPlaysRepository,
			UserRepository userRepository, ArtistUserRepository artistUserRepository, GenreRepository genreRepository) {
		this.albumRepository = albumRepository;
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
		this.songPlaysRepository = songPlaysRepository;
		this.userRepository = userRepository;
		this.artistUserRepository = artistUserRepository;
		this.genreRepository = genreRepository;
	}
	
	public List<Album> getNewReleasesByCurrentMonth() {
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.NEW_RELASES_LIMIT);
		return albumRepository.getNewReleasesOfMonth(pageRequest);
	}
	
	
	public List<Album> getTopAlbumsByWeek() {
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.TOP_ALBUMS_LIMIT,
				Sort.Direction.DESC, BucciConstants.PLAY_COUNT);
		//return albumRepository.topAlbumsOfTheWeek(BucciConstants.TimeAgo.WEEK_AGO,pageRequest);
		return null;
	}
	
	public List<Playlist> getTopPlaylist() {
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.TOP_PLAYLISTS_LIMIT,
				Sort.Direction.DESC, "stats.total_plays");
		return playlistRepository.getTopPlaylistOfAllTime();
	}
	
	public Album getAlbum(int albumId) {
		
	       Album album = albumRepository.findOne(albumId);
	       album.getSongs().size();
	       return album;
	}
	public Album getAlbumNoSongs(int albumId) {
			return albumRepository.findOne(albumId);
	}
	
	public Playlist getPlaylist(int playlistId) {
		
	       Playlist playlist = playlistRepository.findOne(playlistId);
	       playlist.getSongs().size();
	       return playlist;
	}
	
	public Playlist newPlaylist(Playlist playlist) throws BucciException {
		
		User user = userRepository.findOne(playlist.getOwner().getEmail());
		if(user == null) {
			throw new BucciException( "The user does not exist");
		}
		
		if(playlist.getSongs() !=null) {
			List<Song> playlistSongs = new ArrayList<Song>(playlist.getSongs().size());
			for (Song song : playlist.getSongs()) {
				Song songToAdd = songRepository.findOne(song.getId());
				if(songToAdd == null) {
					throw new BucciException("The song '" + song.getName() + "' does not exist");
				}
				playlistSongs.add(songToAdd);
			}
			playlist.setSongs(playlistSongs);
		}

		String artwork = playlist.getArtwork();
		playlist.setOwner(user);
		Playlist newPlaylist = playlistRepository.save(playlist);
		
		if(artwork != null)  {
			try {
				String artworkPath = FileManager.savePlaylistArtwork(artwork, newPlaylist.getId());
				newPlaylist.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("UNABLE TO SAVE ARTWORK");
			}
		}
		return newPlaylist;
	}

	public void saveAlbum(Album album) throws BucciException{
			String artworkString = album.getArtwork();
			Artist albumOwner = artistRepository.findByName(album.getPrimaryArtist().getName());
			album.setPrimaryArtist(albumOwner);
			for(Song song: album.getSongs()) song.setOwner(albumOwner);
			
			Album returnedAlbum = albumRepository.save(album);
			
			albumOwner.getAlbums().add(album); // suppose to add album by adding to artist's album list
			
			
			if(artworkString != null)  {
				try {
					String artworkPath = FileManager.saveAlbumArtwork(artworkString, returnedAlbum.getId());
					album.setArtworkPath(artworkPath);
				} catch (IOException e) {
					
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
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.TOP_ALBUMS_LIMIT,
				Sort.Direction.DESC, BucciConstants.PLAY_COUNT);
		//List<Album> albums = albumRepository.topAlbumsByGenre(genreId, BucciConstants.TimeAgo.TWO_WEEKS_AGO, pageRequest);
		return null;
		//return albumRepository.topAlbumsByGenre(genreId, BucciConstants.TimeAgo.TWO_WEEKS_AGO, pageRequest);
		 
	}
	
	public List<Playlist> getTopPlaylistByGenre(int genreId) {
		PageRequest pageRequest = new PageRequest(BucciConstants.START, BucciConstants.TOP_PLAYLISTS_LIMIT,
				Sort.Direction.DESC, "stats.monthly_plays");
		//return playlistRepository.getTopPlaylistByWeeks(BucciConstants.TimeAgo.TWO_WEEKS_AGO, pageRequest);
		return null;
	}

	/*
	public List<Song> getTopSongsByArtist(int artistId) {
		
		return songRepository.topSongsByArtist(artistId);
	}

	*/
	
	public Album albumOfSong(Song song) throws BucciException {
		if(!songRepository.exists(song.getId())) {
			throw new BucciException("No such song exists");
		}
		
		Album album = albumRepository.findBySongs(song);
		if(album == null) {
			throw new BucciException("Could not find the album of the song");
		}
		
		album.getSongs().size();
		return album;
	}
	
	public List<Album> getFeaturedAlbums(String userEmail) {
		
		int additionalAlbumsNum = 0;
		List<Genre> topGenres = genreRepository.topGenresForCurrentUser(userEmail);
		List<Album> returnAlbums = new ArrayList<>();
		additionalAlbumsNum = populateFeaturedAlbums(Tier.NITRO_DUBS_TIER, topGenres, returnAlbums, additionalAlbumsNum);
		additionalAlbumsNum = populateFeaturedAlbums(Tier.TREX_TIER, topGenres, returnAlbums, additionalAlbumsNum);
		additionalAlbumsNum = populateFeaturedAlbums(Tier.MOONMAN_TIER, topGenres, returnAlbums, additionalAlbumsNum);
		Collections.shuffle(returnAlbums);
		return returnAlbums;
	}
	
	private int populateFeaturedAlbums(Tier tier, List<Genre> genres, List<Album> albums, int rem) {
		
		int populateAmount = 0;
		switch(tier){
			case MOONMAN_TIER: 		populateAmount = BucciConstants.MOONMAN_MAX; break;
			case TREX_TIER: 			populateAmount = BucciConstants.TREX_MAX; break;
			case NITRO_DUBS_TIER: 	populateAmount = BucciConstants.NITRODUBS_MAX; break;
			default: break;
		}
		populateAmount += rem;
		for(Genre genre: genres) {
			
			List<Album> current = albumRepository.albumsByGenreAndTierness(tier.getCode(), genre.getId(), populateAmount);
			albums.addAll(current);
			populateAmount -= current.size();
			if(populateAmount == 0) break;
		}
		
		return populateAmount;
		
	}
	
	public void assignTierToArtistUser(String email, Tier tier) throws BucciException {
		
		ArtistUser artist = artistUserRepository.findOne(email);
		if(artist != null) artist.setTier(tier);
		else throw new BucciException("Artist user with the email: " + email + "does not extist");
	}
}
