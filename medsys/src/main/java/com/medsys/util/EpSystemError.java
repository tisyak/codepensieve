/*
 * 
 */
package com.medsys.util;

import com.medsys.common.model.EpError;

/**
 * The Enum SystemError.
 */
public enum EpSystemError implements EpError {

	//USER ERROR CODES
	/** The invalid user. */
	INVALID_USER("U001"),
	
	/** The no user in session. */
	NO_USER_IN_SESSION("U002"),
	
	/** The user locked. */
	USER_LOCKED("U003"),
	
	USER_ACCESS_DENIED("U004"),
	
	USER_SESSION_EXPIRED("U005"),
	
	//CHALLAN ERROR CODES
	
	ORDER_RSRV_QTY_EXCEEDS_AVBL("OR001"),
	ORDER_PRODUCT_ID_MISMATCH("OR002"),
	
	//PRODUCT INVENTORY ERROR CODES
	PI_DSCRD_QTY_EXCEEDS_AVBL("PI001"),
	PI_ENGG_QTY_EXCEEDS_AVBL("PI002"),
	PI_RLES_QTY_EXCEEDS_ENGG("PI003"),
	PI_SALE_QTY_EXCEEDS_AVBL("PI004"),
	
	//DB ERROR CODES
	
	DB_EXCEPTION("DB000"),
	
	NO_RECORD_FOUND("DB001"),
	
	DUPLICATE_RECORD("DB002"),
	
	//SYSTEM ERROR CODES
	
	/** The system internal error. */
	SYSTEM_INTERNAL_ERROR("SYS001"),
	
	/** The http error. */
	HTTP_ERROR("SYS002"),
	
	/**   QUEUEING EXCEPTION. */
    QUEUEING_EXCEPTION("SYS006"),
	
    /** The fail to refresh master cache. */
    FAIL_TO_REFRESH_MASTER_CACHE("SYS007");
	
    /** The error code. */
	private final String errorCode;
	
	

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Instantiates a new error codes.
	 *
	 * @param errorCode the error code
	 */
	private EpSystemError(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
