/*
 * 
 */
package com.medsys.common.model;


/**
 * The Class Response.
 */
public class ReportsResponse {
	
	
	/** The status. */
	boolean status;
	
	/** The message. */
	String message;
	
	/**
	 * Checks if is status.
	 *
	 * @return true, if is status
	 */
	public boolean isStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	/**
	 * Gets the message code.
	 *
	 * @return the message code
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message code.
	 *
	 * @param messageCode the new message code
	 */
	public void setMessage(String messageCode) {
		this.message = messageCode;
	}
	
	/**
	 * Instantiates a new response.
	 *
	 * @param status the status
	 * @param messageCode the message code
	 */
	public ReportsResponse(boolean status, String message) {
		
		this.status = status;
		this.message = message;
	}
	
	/**
	 * Instantiates a new response.
	 */
	public ReportsResponse() {
		
	}

	@Override
	public String toString() {
		return "ReportsResponse [status=" + status + ", message=" + message + "]";
	}
	
	
}
