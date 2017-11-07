package com.buccitunes.model;

public class Password {
	private String password;
	
	public String getPassword() {
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
