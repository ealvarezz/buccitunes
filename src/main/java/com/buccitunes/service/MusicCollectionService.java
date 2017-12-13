package com.buccitunes.service;


import java.io.IOException;
import java.security.acl.Owner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.buccitunes.constants.Tier;
import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.GenreRepository;
import com.buccitunes.dao.PlaylistRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.SongMonthlyStatRepository;
import com.buccitunes.dao.SongPlaysRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.dao.UserActivityRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.jsonmodel.PlaylistPage;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciFailure;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Genre;
import com.buccitunes.model.Playlist;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.SongMonthlyStat;
import com.buccitunes.model.SongPlays;
import com.buccitunes.model.User;
import com.buccitunes.model.UserActivity;
import com.buccitunes.resultset.AlbumWithStats;

@Service
@Transactional
public class MusicCollectionService {
	
	@Autowired
	private BucciConstants constants;
	
	boolean alter = true; // just for testing purposes, must be removed during production
	private final AlbumRepository albumRepository;
	private final PlaylistRepository playlistRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	private final SongPlaysRepository songPlaysRepository;
	private final UserRepository userRepository;
	private final GenreRepository genreRepository;
	private final ArtistUserRepository artistUserRepository;
	private final SongMonthlyStatRepository songMonthlyStatRepository;

	private final UserActivityRepository userActivityRepository;
	
	public MusicCollectionService(AlbumRepository albumRepository, PlaylistRepository playlistRepository,
			SongRepository songRepository, ArtistRepository artistRepository, SongPlaysRepository songPlaysRepository,
			UserRepository userRepository, ArtistUserRepository artistUserRepository, GenreRepository genreRepository,
			SongMonthlyStatRepository songMonthlyStatRepository, UserActivityRepository userActivityRepository
			) {
		this.albumRepository = albumRepository;
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
		this.songPlaysRepository = songPlaysRepository;
		this.userRepository = userRepository;
		this.artistUserRepository = artistUserRepository;
		this.genreRepository = genreRepository;
		this.songMonthlyStatRepository = songMonthlyStatRepository;
		this.userActivityRepository = userActivityRepository;
	}
	
	public List<Album> getNewReleasesByCurrentMonth() {
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getNewReleasesLimit());
		return albumRepository.getNewReleasesOfMonth(pageRequest);
	}
	
	
	public List<Album> getTopAlbumsByWeek() {
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getTopAlbumsLimit(),
				Sort.Direction.DESC, constants.getPlayCount());
		//return albumRepository.topAlbumsOfTheWeek(BucciConstants.TimeAgo.WEEK_AGO,pageRequest);
		return null;
	}
	
	public List<Playlist> getTopPlaylist() {
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getTopPlaylistsLimit(),
				Sort.Direction.DESC, "stats.total_plays");
		return playlistRepository.getTopPlaylistOfAllTime();
	}
	
	public Album getAlbum(int albumId, String email) {
		
		User user = userRepository.findOne(email);
		Album album = albumRepository.findOne(albumId);
		
		user.getSavedAlbums().size();
		if(user.getSavedAlbums()!=null && user.getSavedAlbums().contains(album)) album.setFollowing(true);
		
		album.getSongs().size();
	    return album;
	}
	public Album getAlbumNoSongs(int albumId) {
			return albumRepository.findOne(albumId);
	}
	
	public Playlist getPlaylist(int playlistId, String email) {
		
		User user = userRepository.findOne(email);
		Playlist playlist = playlistRepository.findOne(playlistId);
		
		user.getFollowingPlaylists().size();
		if(user.getFollowingPlaylists() !=null && user.getFollowingPlaylists().contains(playlist)) playlist.setFollowing(true);
		
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
		
		UserActivity activity = new UserActivity(user, new Date());
		activity.setFeed(user.getEmail()+" made a new playlist: "+playlist.getTitle());
		userActivityRepository.save(activity);
		
		if(artwork != null)  {
			try {
				String artworkPath = FileManager.savePlaylistArtwork(artwork, newPlaylist.getId());
				newPlaylist.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("Unable to save artwork");
			}
		}
		return newPlaylist;
	}
	
	public PlaylistPage changePlaylist(PlaylistPage playlistPage, User user) throws BucciException {
		Playlist playlist = playlistRepository.findOne(playlistPage.getPlaylist().getId());
		List<Song> songsToAdd = playlistPage.getSongsToAdd();
		List<Song> songsToRemove = playlistPage.getSongsToRemove();
		String artwork = playlistPage.getPlaylist().getArtwork();
		
		
		if(playlist == null) {
			throw new BucciException("Playlist does not exist");
		}
		
		if(!playlist.getOwner().getEmail().equals(user.getEmail())) {
			throw new BucciException("You do not have permissions to change this playlist!");
		}
		
		if(songsToAdd != null) {
			for(Song song : songsToAdd) {
				Song addSong = songRepository.findOne(song.getId());
				if(addSong == null) {
					throw new BucciException(song.getName() + "does not exist");
				}
				playlist.addSong(addSong);
			}
		}
		
		if(songsToRemove != null) {
			for(Song song : songsToRemove) {
				Song removeSong = songRepository.findOne(song.getId());
				if(removeSong == null) {
					throw new BucciException(song.getName() + "does not exist");
				}
				playlist.removeSong(removeSong);
			}
		}
		
		playlist.updateInfo(playlistPage.getPlaylist());
		
		if(artwork != null)  {
			try {
				String artworkPath = FileManager.savePlaylistArtwork(artwork, playlist.getId());
				playlist.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("Unable to save artwork");
			}
		}
		
		playlistPage.setPlaylist(playlist);
		return playlistPage;
	}
	
	public void deletePlaylist(int playlistId, User user) throws BucciException {
		Playlist playlist = playlistRepository.findOne(playlistId);
		
		
		if(playlist == null) {
			throw new BucciException("Playlist does not exist");
		}
		
		if(!playlist.getOwner().getEmail().equals(user.getEmail()) ) {
			throw new BucciException("You do not have permissions to delete this playlist!");
		}
		
		
		try {
			FileManager.removePlaylistResources(playlist);
		} catch (IOException e) {
			throw new BucciException("Failed to remove album resources, try again.");
		}
		playlistRepository.delete(playlist);
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
	
	public Song playSong(User loggedUser, Song song) throws BucciException {
		User user = userRepository.findOne(loggedUser.getEmail());
		song = songRepository.findOne(song.getId());
		if(song == null) {
			throw new BucciException("Song does not exist");
		}
		
		SongPlays songPlayed;
		if(user.isInPrivateMode()) {
			songPlayed = new SongPlays(null, song);
		} else {
			songPlayed = new SongPlays(user, song);
		}
		
		songPlaysRepository.save(songPlayed);
		
		return song;
	}
	
	public List<Song> getTopSongs(){
		
		return songRepository.getCurrentTopSongs();
	}
	
	public List<Album> getTopAlbumsByGenre(int genreId) {
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getTopAlbumsLimit(),
				Sort.Direction.DESC, constants.getPlayCount());
		//List<Album> albums = albumRepository.topAlbumsByGenre(genreId, BucciConstants.TimeAgo.TWO_WEEKS_AGO, pageRequest);
		return null;
		//return albumRepository.topAlbumsByGenre(genreId, BucciConstants.TimeAgo.TWO_WEEKS_AGO, pageRequest);
		 
	}
	
	public List<Playlist> getTopPlaylistByGenre(int genreId) {
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getTopPlaylistsLimit(),
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
			case MOONMAN_TIER: 		populateAmount = constants.getMoonmanMax(); break;
			case TREX_TIER: 			populateAmount = constants.getTrexMax(); break;
			case NITRO_DUBS_TIER: 	populateAmount = constants.getNitrodubsMax(); break;
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
	
	public void getAlbumsOfSavedSongs(User user) {
		//user.ge
	}
	
	public Song saveAudioFile(Song audioSong) throws BucciException {
		
		Song song = songRepository.findOne(audioSong.getId());
		
		if(song == null) {
			throw new BucciException("Song not found");
		}
		
		int duration = audioSong.getDuration();
		if(duration == 0) {
			throw new BucciException("Durantion of song is needed");
		}
		try {
			String audioPath = FileManager.saveSong(audioSong.getAudio(), song.getId());
			song.setAudioPath(audioPath);
			song.setDuration(duration);
		} catch (IOException e) {
			throw new BucciException("Unable to save song");
		}
		
		return song;
	}
	
	public void addSongToPlaylist(int playlistId, int songId) {
		
		Song song = songRepository.findOne(songId);
		Playlist playlist = playlistRepository.findOne(playlistId);
		playlist.addSong(song);
		
	}
	
	public Song getSong(int songId) {
		
		Song song = songRepository.findOne(songId);
		
		return song;
		
	}
	
	public String getSongLyrics(int songId) {
		Song song = songRepository.findOne(songId);
		if(song.getLyrics() !=null) {
			return song.getLyrics().getLyric();
		}
		else {
			return "";
		}
	}

	
}
