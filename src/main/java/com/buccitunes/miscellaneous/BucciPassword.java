package com.buccitunes.miscellaneous;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BucciPassword {
	public static String encryptPassword(String plainText) {
		return BCrypt.hashpw(plainText, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String plainText, String hashed) {
		return BCrypt.checkpw(plainText, hashed);
	}
}
