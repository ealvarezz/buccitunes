package com.buccitunes.model;
import org.springframework.security.crypto.bcrypt.BCrypt;



public class Password {
	private String password;
	
	
	public String getPassword() {
		//BCrypt.hashpw(password, BCrypt.gensalt());
		
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Password(String plainText) {
		
	}
	
	public boolean checkPassword(String plainText) {
		return false;
	}
}
