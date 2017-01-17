package com.medsys.exception;

/**
 * The Enum Severity.
 */
public enum Severity {
    
    /** The fatal. */
    FATAL("4"),
    
    /** The error. */
    ERROR("3"),
    
    /** The warn. */
    WARN("2"),
    
    /** The info. */
    INFO("1");
    
    /** The str severity code. */
    private String strSeverityCode;

    /**
     * Gets the str severity code.
     *
     * @return the str severity code
     */
    public String getStrSeverityCode() {
        return strSeverityCode;
    }

    /**
     * Sets the str severity code.
     *
     * @param strSeverityCode the new str severity code
     */
    public void setStrSeverityCode(String strSeverityCode) {
        this.strSeverityCode = strSeverityCode;
    }

    /**
     * Instantiates a new severity.
     *
     * @param strSeverityCode the str severity code
     */
    private Severity(String strSeverityCode) {
        this.strSeverityCode = strSeverityCode;
    }

}
