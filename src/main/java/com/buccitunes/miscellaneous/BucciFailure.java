package com.buccitunes.miscellaneous;

public class BucciFailure<T> {
	private T object;
	private String reason;
	
	public BucciFailure(String reason) {
		this.object = null;
		this.reason = reason;
	}
	
	public BucciFailure(T object, String reason) {
		this.object = object;
		this.reason = reason;
	}
	
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
