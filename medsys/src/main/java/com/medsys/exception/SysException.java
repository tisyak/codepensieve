/*
 * 
 */
package com.medsys.exception;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.common.model.EpError;

/**
 * The Class SysException.
 */
public class SysException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The reason. */
    private String reason;

    /** The error code. */
    private EpError errorCode;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(SysException.class);

    /** The errors. */
    private static ResourceBundle errors = ResourceBundle
            .getBundle("system-error-messages");

    /**
     * Instantiates a new MEDSYS exception.
     * 
     * @param objectName
     *            the object name
     * @param objectIdName
     *            the object id name
     * @param ObjectIdValue
     *            the object id value
     * @param errCode
     *            the err code
     */
    public SysException(String objectName, String objectIdName,
            String ObjectIdValue, EpError errCode) {

        // String cause=errors.getString(errCode.getErrorCode());
        // super(cause);
        // super(environment.getProperty(errCode.getErrorCode()));
        try {
            this.reason = errors.getString(errCode.getErrorCode());
        } catch (MissingResourceException e) {
            logger.error("Error for errorcode:" + errCode
                    + " not found in properties file!.");
        }
        this.errorCode = errCode;
        logger.error("Exception for " + objectName + " - " + objectIdName
                + " :" + ObjectIdValue + " - Cause: Error Code("
                + errCode + ") - " + reason);

    }

    /**
     * Instantiates a new MEDSYS exception.
     * 
     * @param objectName
     *            the object name
     * @param ObjectIdValue
     *            the object id value
     * @param errCode
     *            the err code
     */
    public SysException(String objectName, Object ObjectIdValue,
            EpError errCode) {
        // String cause=errors.getString(errCode.getErrorCode());
        // super(cause);
        // super(environment.getProperty(errCode.getErrorCode()));
        try {
            this.reason = errors.getString(errCode.getErrorCode());
        } catch (MissingResourceException e) {
            logger.error("Error for errorcode:" + errCode
                    + " not found in properties file!.");
        }
        this.errorCode = errCode;
        logger.error("Exception for " + objectName + " :" + ObjectIdValue
                + " - Cause: Error Code(" + errCode + ") - " + reason);

    }

    /**
     * Gets the reason.
     * 
     * @return the reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public EpError getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     * 
     * @param errorCode
     *            the new error code
     */
    public void setErrorCode(EpError errorCode) {
        this.errorCode = errorCode;
    }
}
