package com.buccitunes.jsonmodel;

import java.util.List;

import com.buccitunes.miscellaneous.BucciFailure;

public abstract class BaseJsonModel<T> {
	private List<BucciFailure<T>> failedRequests;

	public List<BucciFailure<T>> getFailedRequests() {
		return failedRequests;
	}

	public void setFailedRequests(List<BucciFailure<T>> failedRequests) {
		this.failedRequests = failedRequests;
	}
}
