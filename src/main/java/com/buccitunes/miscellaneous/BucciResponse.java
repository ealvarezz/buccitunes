package com.buccitunes.miscellaneous;

public class BucciResponse<T> {

	private String message;
	
	private boolean successful;
	
	private T response;
	
	private String status;
	
	public BucciResponse(String message, boolean successful, T response, String status) {
		this.message = message;
		this.successful = successful;
		this.response = response;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
