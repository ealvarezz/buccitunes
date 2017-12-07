package com.buccitunes.miscellaneous;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.buccitunes.model.Album;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.Concert;
import com.buccitunes.model.Password;
import com.buccitunes.model.RequestedAlbum;
import com.buccitunes.model.RequestedArtist;
import com.buccitunes.model.RequestedConcert;
import com.buccitunes.model.RequestedSong;
import com.buccitunes.model.Song;
import com.buccitunes.model.User;

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

	private String siteURL;
	
	private String ApprovedAlbumSubject;
	private String ApprovedAlbumText;
		  
	private String ApprovedSongSubject;
	private String ApprovedSongText;
		  
	private String DeniedAlbumSubject;
	private String DeniedAlbumText;  
		    
	private String DeniedSongSubject;
	private String DeniedSongText;
	
	private String ApprovedConcertSubject;
	private String ApprovedConcertText;
		  
	private String DeniedConcertSubject;
	private String DeniedConcertText;
	
	private String AccountConfirmationSubject;
	private String AccountConfirmationText;
	 
	private String resetEmailSubject;
	private String resetEmailText;
	
	private String ApprovedArtistSubject;
	private String ApprovedArtistText;
	
	private String DeniedArtistSubject;
	private String DeniedArtistText;
	
	
	public void mailApprovedAlbumRequest(ArtistUser user, Album album) throws MessagingException{
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(ApprovedAlbumSubject);
        helper.setText(String.format(ApprovedAlbumText, album.getTitle()));
        javaMailSender.send(mail);
	}
	
	public void mailDeniedAlbumRequest(ArtistUser user, RequestedAlbum album) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(DeniedAlbumSubject);
        helper.setText(String.format(DeniedAlbumText, album.getTitle()));
        javaMailSender.send(mail);		
	}
	
	
	public void mailApprovedSongRequest(ArtistUser user, Song song) throws BucciException {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        helper.setTo(user.getEmail());
	        helper.setSubject(ApprovedSongSubject);
	        helper.setText(String.format(ApprovedSongText, song.getName()));
	        javaMailSender.send(mail);
		} catch (MessagingException e) {
			throw new BucciException(e.getMessage());
		}
	}

	
	public void mailDeniedSongRequest(ArtistUser user, RequestedSong song) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(DeniedSongSubject);
        helper.setText(String.format(DeniedSongText, song.getName()));
        javaMailSender.send(mail);
	}

	public void mailApprovedConcertRequest(ArtistUser user, Concert concert) throws BucciException {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        helper.setTo(user.getEmail());
	        helper.setSubject(ApprovedConcertSubject);
	        helper.setText(String.format(ApprovedConcertText, concert.getName()));
	        javaMailSender.send(mail);
		} catch (MessagingException e) {
			throw new BucciException(e.getMessage());
		}
	}
	
	public void mailApprovedArtistRequest(ArtistUser user) throws BucciException {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        helper.setTo(user.getEmail());
	        helper.setSubject(ApprovedArtistSubject);
	        helper.setText(String.format(ApprovedArtistText, user.getArtist().getId()));
	        javaMailSender.send(mail);
		} catch (MessagingException e) {
			throw new BucciException(e.getMessage());
		}
	}
	
	public void mailDeniedArtistRequest(RequestedArtist requested) throws BucciException {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        helper.setTo(requested.getRequester().getEmail());
	        helper.setSubject(DeniedArtistSubject);
	        helper.setText(String.format(DeniedArtistText, requested.getName()));
	        javaMailSender.send(mail);
		} catch (MessagingException e) {
			throw new BucciException(e.getMessage());
		}
	}
	
	public void mailDeniedConcertRequest(ArtistUser user, RequestedConcert concert) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(DeniedConcertSubject);
        helper.setText(String.format(DeniedConcertText, concert.getName()));
        javaMailSender.send(mail);
	}
	
	public void mailAccountConfirmation(User user) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(AccountConfirmationSubject);
        helper.setText(String.format(AccountConfirmationText, user.getEmail()));
        javaMailSender.send(mail);
	}
	
	public void sendPasswordReset(String email, String hashedURL) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(email);
        helper.setSubject(resetEmailSubject);
        helper.setText(String.format(resetEmailText, siteURL+"/recover/"+email+"/"+hashedURL ));
        javaMailSender.send(mail);
	}	
	
	public void sendResetConfirmation(String email) throws MessagingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(email);
        helper.setSubject("BucciTunes Password Change Confirmation");
        helper.setText(String.format("Your password has been changed, if this wasn't you please email buccitunes@gmail.com"));
        javaMailSender.send(mail);
	}	
	
	
	
	public void setAccountConfirmationSubject(String accountConfirmationSubject) {
		AccountConfirmationSubject = accountConfirmationSubject;
	}

	public void setAccountConfirmationText(String accountConfirmationText) {
		AccountConfirmationText = accountConfirmationText;
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

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setApprovedConcertSubject(String approvedConcertSubject) {
		ApprovedConcertSubject = approvedConcertSubject;
	}

	public void setApprovedConcertText(String approvedConcertText) {
		ApprovedConcertText = approvedConcertText;
	}

	public void setDeniedConcertSubject(String deniedConcertSubject) {
		DeniedConcertSubject = deniedConcertSubject;
	}

	public void setDeniedConcertText(String deniedConcertText) {
		DeniedConcertText = deniedConcertText;
	}

	public void setResetEmailSubject(String resetEmailSubject) {
		this.resetEmailSubject = resetEmailSubject;
	}

	public void setResetEmailText(String resetEmailText) {
		this.resetEmailText = resetEmailText;
	}

	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}

	public void setApprovedArtistSubject(String approvedArtistSubject) {
		ApprovedArtistSubject = approvedArtistSubject;
	}

	public void setApprovedArtistText(String approvedArtistText) {
		ApprovedArtistText = approvedArtistText;
	}

	public void setDeniedArtistSubject(String deniedArtistSubject) {
		DeniedArtistSubject = deniedArtistSubject;
	}

	public void setDeniedArtistText(String deniedArtistText) {
		DeniedArtistText = deniedArtistText;
	}
}
