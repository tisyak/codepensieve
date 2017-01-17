package com.medsys.util;

import com.medsys.common.model.Response;

/**
 * The Class EmailResponse.
 */
public class EmailResponse {
	
	/** The total mail count. */
	int totalMailCount;
	
	/** The total unread mail count. */
	int totalUnreadMailCount;
	
	/** The response. */
	Response response;
	
	/**
	 * Gets the total mail count.
	 *
	 * @return the total mail count
	 */
	public int getTotalMailCount() {
		return totalMailCount;
	}
	
	/**
	 * Sets the total mail count.
	 *
	 * @param totalMailCount the new total mail count
	 */
	public void setTotalMailCount(int totalMailCount) {
		this.totalMailCount = totalMailCount;
	}
	
	/**
	 * Gets the total unread mail count.
	 *
	 * @return the total unread mail count
	 */
	public int getTotalUnreadMailCount() {
		return totalUnreadMailCount;
	}
	
	/**
	 * Sets the total unread mail count.
	 *
	 * @param totalUnreadMailCount the new total unread mail count
	 */
	public void setTotalUnreadMailCount(int totalUnreadMailCount) {
		this.totalUnreadMailCount = totalUnreadMailCount;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(Response response) {
		this.response = response;
	}
	
	
	
}
