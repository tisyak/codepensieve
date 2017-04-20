/*
 * 
 */
package com.medsys.ui.util;

/**
 * The Constants class of UIConstants. For all Session,Request,Message Codes and
 * other UI related attribute placeholder names.
 */
public enum UIConstants {

    /***************************** SESSION ATTRIBUTE CONSTANTS* ***********************************. */

    SESSION_USER("adminUserToken"),

    /***************************** CAPTCHA ATTRIBUTE* ***********************************. */

    JCAPTCHARESPONSE("j_captcha_response"),

    /***************************** JQGrid Constants ************************************/
    
    RECORDS_ZERO("0"),
    TOTAL_ZERO("0"),
    PAGE_SINGLE("1"),
    
    /***************************** Math Calculation Constants ************************************/
    MATH_PRECISION_CONTEXT("4"),
    EMPTY_QTY("0"),
       
    /***************************** ENCODED URL MANIPULTATION CONSTANTS ***********************************. */
    /**
     * Encoded URL replaces the '+' signs with ' '(space character).Constant to
     * identify this ' '(space character).
     */
    ENCODED_URL_SPACE(" "),
    /**
     * Encoded URL replaces the '+' signs with ' '(space character).Constant to
     * replace this ' '(space character).
     */
    ENCODED_URL_SPACE_REPLACE_CHAR("+"),


    /** The date format. */
    DATE_FORMAT("dd MMM yyyy"),

    /** The date time format. */
    DATE_TIME_FORMAT("dd MMM yyyy hh:mm:ss aaa"),


    /***Constant placeholders for different message classifications****/
    
    
    /** The first placeholder for message text. */
    MSG_FOR_USER("msg"),

    /** The first argument placeholder for messages. */
    MSG_FOR_USER_WITH_ARG("arg1"),

    /** The second argument placeholder for messages that have 2 arguments. */
    MSG_FOR_USER_WITH_ARG_2("arg2"),

    /** The msg for user failure. */
    MSG_FOR_USER_FAILURE("msgFailure"),

    /** The msg for user success. */
    MSG_FOR_USER_SUCCESS("msgSuccess"),

    /** The msg for user error. */
    MSG_FOR_USER_ERROR("errorMsg"),

    /** The msg for user success. */
    EP_ERROR("epError"),

    /** The msg for system error. */
    MSG_FOR_SYSTEM_ERROR("sysErrorMsg"),

    /** The msg for user warn. */
    MSG_FOR_USER_WARN("warnMsg"),

    /** The log out message . */
    MSG_FOR_LOG_OUT("msg.logout")
    ;

    /** The value. */
    private final String value;

    /**
     * Instantiates a new UI constants.
     * 
     * @param value
     *            the value
     */
    UIConstants(String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

}
