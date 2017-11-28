package com.buccitunes.miscellaneous;

import com.buccitunes.model.AdminUser;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;

public class BucciPrivilege {
	
	public static final String USER = "User";
	public static final String PREMIUM = "Premium";
	public static final String ARTIST = "Artist";
	public static final String ADMIN = "Admin";
	
	
	public static boolean isPremium(User user) {
		return (user instanceof PremiumUser);
	}
	
	public static boolean isArtist(User user) {
		return (user instanceof ArtistUser);
	}
	
	public static boolean isAdmin(User user) {
		return (user instanceof AdminUser);
	}
	
	public static User setRole(User user) {
		if(isPremium(user)) {
			user.setRole(PREMIUM);
		}
		else if(isArtist(user)){
			user.setRole(ARTIST);
		}
		else if(isAdmin(user)){
			user.setRole(ADMIN);
		}
		else {
			user.setRole(USER);
		}
		return user;
	}
}
