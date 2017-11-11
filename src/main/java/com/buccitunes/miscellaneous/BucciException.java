package com.buccitunes.miscellaneous;

public class BucciException extends Exception {
	
	private String errMessage;
	
	public BucciException (String message) {
		this.errMessage = message;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErMessage(String message) {
		this.errMessage = message;
	}
	
	
}
