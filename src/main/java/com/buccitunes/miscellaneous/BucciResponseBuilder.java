package com.buccitunes.miscellaneous;

public class BucciResponseBuilder {
	
	public static <T> BucciResponse<T> successfulResponseMessage(String message, T response) {
		
		boolean successful = true;
		String status = "200 OK";
		
		BucciResponse<T> bucci = new BucciResponse<T>(message, successful, response, status);
		
		return bucci;
	}
	
	public static <T> BucciResponse<T> successfulResponse(T response) {
			
			boolean successful = true;
			String status = "200 OK";
			String message = "";
			
			BucciResponse<T> bucci = new BucciResponse<T>(message, successful, response, status);
			
			return bucci;
	}
	
	public static <T> BucciResponse<T> failedResponse(String message) {
			boolean successful = false;
			String status = "400 Bad Request";
			
			BucciResponse<T> bucci = new BucciResponse<T>(message, successful, null, status);
			
			return bucci;
	}
}
