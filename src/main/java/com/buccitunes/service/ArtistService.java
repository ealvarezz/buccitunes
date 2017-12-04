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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buccitunes.constants.Tier;
import com.buccitunes.dao.AlbumRepository;
import com.buccitunes.dao.ArtistRepository;
import com.buccitunes.dao.ArtistUserRepository;
import com.buccitunes.dao.RequestedAlbumRepository;
import com.buccitunes.dao.RequestedConcertRepository;
import com.buccitunes.dao.RequestedSongRepository;
import com.buccitunes.dao.SongRepository;
import com.buccitunes.miscellaneous.BucciConstants;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.BucciPrivilege;
import com.buccitunes.miscellaneous.FileManager;
import com.buccitunes.model.Album;
import com.buccitunes.model.Artist;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedConcert;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;

@Service
@Transactional
public class ArtistService {
	
	@Autowired
	private BucciConstants constants;
	
	
	
	private final ArtistRepository artistRepository;
	private final AlbumRepository albumRepository;
	private final RequestedAlbumRepository requestedAlbumRepository;
	private final ArtistUserRepository artistUserRepository;
	private final SongRepository songRepository;
	private final RequestedSongRepository requestedSongRepository;
	private final RequestedConcertRepository requestedConcertRepository;
	
	public ArtistService(ArtistRepository artistRepository, RequestedAlbumRepository requestedAlbumRepository,
			ArtistUserRepository artistUserRepository, SongRepository songRepository, 
			RequestedSongRepository requestedSongRepository, AlbumRepository albumRepository,
			RequestedConcertRepository requestedConcertRepository) {
		
		this.albumRepository = albumRepository;
		this.artistRepository = artistRepository;
		this.requestedAlbumRepository = requestedAlbumRepository;
		this.artistUserRepository = artistUserRepository;
		this.songRepository = songRepository;
		this.requestedSongRepository = requestedSongRepository;
		this.requestedConcertRepository = requestedConcertRepository;
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
			artist.getUpcomingConcerts();
			for(Album album : artist.getAlbums()) {
				album.getSongs().size();
			}
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
			savedArtist = (ArtistUser) BucciPrivilege.setRole(savedArtist);
			
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
		PageRequest pageRequest = new PageRequest(constants.getStart(), constants.getTopSongsLimit(),
				Sort.Direction.DESC, constants.getPlayCount());
		//return songRepository.getTopSongsOfArtist(BucciConstants.TimeAgo.ALL_TIME, artistId, pageRequest);
		return null;
		
	}
	
	public RequestedAlbum requestNewAlbum(RequestedAlbum requested, ArtistUser artistUser) throws BucciException {
		
		String artwork = requested.getArtwork();
		List<RequestedSong> audioSongs = requested.getSongs();
		
		artistUser = artistUserRepository.findOne(artistUser.getEmail());
		requested.setArtistRequester(artistUser);
		RequestedAlbum requestedAlbum = requestedAlbumRepository.save(requested);
		
		if(artwork != null) {
			try {
				String artworkPath = FileManager.saveRequestedAlbumArtwork(artwork, requestedAlbum.getId());
				requestedAlbum.setArtworkPath(artworkPath);
			} catch (IOException e) {
				throw new BucciException("Unable to save artwork");
			}
		}
		
		if(audioSongs != null) {
			requestedAlbum.getSongs().size();
			
			for (RequestedSong songWithAudio : audioSongs) {
				if(songWithAudio.getAudio() != null) {
					try {
						String audioPath = FileManager.saveRequestedSong(songWithAudio.getAudio(), songWithAudio.getId());
						songWithAudio.setAudioPath(audioPath);
					} catch (IOException e) {
						System.out.println("\n================\n" + songWithAudio.getName() + " could not save!!!==========\n");
					}
				}
			}
		}		
		return requestedAlbum;
	}
	
	public void requestSongToAlbum(RequestedSong requestedSong, ArtistUser user) throws BucciException{
		
		if(requestedSong.getAlbum() == null) {
			throw new BucciException("No album specified");
		}
		RequestedAlbum album = requestedAlbumRepository.findOne(requestedSong.getAlbum().getId());
		if(album == null) {
			throw new BucciException("Album does not exist");
		}
		
		ArtistUser artistUser = artistUserRepository.findOne(user.getEmail());
		if(artistUser.getArtist().getId() != album.getPrimaryArtist().getId()) {
			throw new BucciException("You do not own the album in order to add a song");
		}
			
		requestedSong.setArtist(album.getPrimaryArtist());
		requestedSong.setAlbum(album);
		requestedSong.setRequester(artistUser);
		requestedSongRepository.save(requestedSong);
	}
	
	
	public RequestedConcert requestNewConcert(RequestedConcert requested, ArtistUser artistUser) throws BucciException {
		artistUser = artistUserRepository.findOne(artistUser.getEmail());
		requested.setRequester(artistUser);
		RequestedConcert requestedConcert = requestedConcertRepository.save(requested);	
			
		return requestedConcert;
	}
	
	
	
	
	
	public void deleteSongFromAlbum(int songId, int albumId) {
		
		Album album = albumRepository.findOne(albumId);
		Song song = songRepository.findOne(songId);
		songRepository.delete(songId);
		
		album.getSongs().remove(song);
	}
	
	public void deleteAlbum(int albumId){
		albumRepository.delete(albumId);
	}
	
public Song saveAudioFile(Song audioSong, User user) throws BucciException {
		
		Song song = songRepository.findOne(audioSong.getId());
		
		if(song == null) {
			throw new BucciException("Song not found");
		}
		
		if(BucciPrivilege.isArtist(user)) {
			Artist artist = ((ArtistUser) user).getArtist();
			
			if(artist.getId() != song.getOwner().getId()) {
				throw new BucciException("You do not own this song!");
			}
		}
		
		int durantion = audioSong.getDuration();
		if(durantion == 0) {
			throw new BucciException("Durantion of song is needed");
		}
		try {
			String audioPath = FileManager.saveSong(audioSong.getAudio(), song.getId());
			song.setAudioPath(audioPath);
		} catch (IOException e) {
			throw new BucciException("Unable to save song");
		}
		
		return song;
	}
	
}
