package com.medsys.util;

import com.medsys.common.model.EpError;

public enum EpFieldError implements EpError{
    
    //FIELD ERROR CODE
    REQUIRED_FIELD("error.required_field.message"),
    
    /**  Invalid Captcha. */
    INVALID_CAPTCHA("error.invalid.captcha");
    
    
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
    private EpFieldError(String errorCode) {
        this.errorCode = errorCode;
    }
}
