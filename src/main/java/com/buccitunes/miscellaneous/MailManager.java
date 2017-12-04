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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="email")
public class MailManager {
	
	@Autowired
    public JavaMailSender javaMailSender;

	
	private String ApprovedAlbumSubject;
	private String ApprovedAlbumText;
		  
	private String ApprovedSongSubject;
	private String ApprovedSongText;
		  
	private String DeniedAlbumSubject;
	private String DeniedAlbumText;  
		    
	private String DeniedSongSubject;
	private String DeniedSongText;    
	
	 
	
	public void sendApprovedAlbumRequest(ArtistUser user, Album album) throws MessagingException{
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(ApprovedAlbumSubject);
        helper.setText(String.format(ApprovedAlbumText, album.getTitle()));
        javaMailSender.send(mail);
	}
	
	public void sendDeniedAlbumRequestToUser(ArtistUser user, RequestedAlbum album) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(DeniedAlbumSubject);
        helper.setText(String.format(DeniedAlbumText, album.getTitle()));
        javaMailSender.send(mail);		
	}
	
	
	public void sendApprovedSongRequest(ArtistUser user, Song song) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(ApprovedSongSubject);
        helper.setText(String.format(ApprovedSongText, song.getName()));
        javaMailSender.send(mail);
	}

	
	public void sendDeniedSongRequest(ArtistUser user, RequestedSong song) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(DeniedSongSubject);
        helper.setText(String.format(DeniedSongText, song.getName()));
        javaMailSender.send(mail);
	}

	public void setApprovedAlbumSubject(String approvedAlbumSubject) {
		ApprovedAlbumSubject = approvedAlbumSubject;
	}

	public void setApprovedAlbumText(String approvedAlbumText) {
		ApprovedAlbumText = approvedAlbumText;
	}

	public void setApprovedSongSubject(String approvedSongSubject) {
		ApprovedSongSubject = approvedSongSubject;
	}

	public void setApprovedSongText(String approvedSongText) {
		ApprovedSongText = approvedSongText;
	}

	public void setDeniedAlbumSubject(String deniedAlbumSubject) {
		DeniedAlbumSubject = deniedAlbumSubject;
	}

	public void setDeniedAlbumText(String deniedAlbumText) {
		DeniedAlbumText = deniedAlbumText;
	}

	public void setDeniedSongSubject(String deniedSongSubject) {
		DeniedSongSubject = deniedSongSubject;
	}

	public void setDeniedSongText(String deniedSongText) {
		DeniedSongText = deniedSongText;
	}
	
}
