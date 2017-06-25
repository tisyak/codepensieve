/*
 * 
 */
package com.medsys.common.model;

/**
 * The Class Response.
 */
public class Response {

	/** The status. */
	boolean status;

	/** The error. */
	EpError error;

	String remarks;

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
	 * @param status
	 *            the new status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public EpError getError() {
		return error;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setError(EpError errorCode) {
		this.error = errorCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param status
	 *            the status
	 * @param errorCode
	 *            the error code
	 */
	public Response(boolean status, EpError error) {

		this.status = status;
		this.error = error;
	}

	/**
	 * Instantiates a new response.
	 */
	public Response() {

	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", error=" + error + ", remarks=" + remarks + "]";
	}

}
