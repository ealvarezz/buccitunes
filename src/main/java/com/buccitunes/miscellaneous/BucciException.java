package com.buccitunes.miscellaneous;

public class BucciException extends Exception {
	
	private String message;
	
	public BucciException (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
