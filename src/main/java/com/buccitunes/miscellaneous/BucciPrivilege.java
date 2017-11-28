package com.buccitunes.miscellaneous;

import com.buccitunes.model.AdminUser;
import com.buccitunes.model.ArtistUser;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;

public class BucciPrivilege {
	
	public static boolean isPremium(User user) {
		return (user instanceof PremiumUser);
	}
	
	public static boolean isArtist(User user) {
		return (user instanceof ArtistUser);
	}
	
	public static boolean isAdmin(User user) {
		return (user instanceof AdminUser);
	}
}
