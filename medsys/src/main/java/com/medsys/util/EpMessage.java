package com.medsys.util;

public enum EpMessage {

	MSG_FOR_SUCCESS_REFRESH_MASTER_CACHE(
			"msg.system.refresh.mastercache.success"),

	MSG_FOR_SUCCESSFUL_USER_VALIDATION("msg.validate.user.success"),

	MSG_FOR_SUCCESSFUL_LOG_OUT("msg.user.success.logout"),
	
	MSG_FOR_EMPTY_SEARCH_RESULT("msg.empty.search.result")

	;

	/** The error code. */
	private final String msgCode;

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getMsgCode() {
		return msgCode;
	}

	/**
	 * Instantiates a new error codes.
	 * 
	 * @param errorCode
	 *            the error code
	 */
	private EpMessage(String msgCode) {
		this.msgCode = msgCode;
	}

}
