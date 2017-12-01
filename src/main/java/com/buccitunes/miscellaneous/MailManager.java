package com.buccitunes.miscellaneous;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.buccitunes.model.Album;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailManager {

	public static void sendApprovedAlbumRequest(ArtistUser user, Album album) {
		
	}
	
	public static void sendUnApprovedAlbumRequestToUser(ArtistUser user, RequestedAlbum album) {
		
	}
	
	public static void sendApprovedSongRequest(ArtistUser user, Song song) {
		
	}

	public static void sendUnApprovedSongRequest(ArtistUser user, RequestedSong song) {
	
	}
}
