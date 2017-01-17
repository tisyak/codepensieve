package com.medsys.ui.util;

public interface UIValidation {
    

    /**
     * Gets the pattern.
     *
     * @return the pattern
     */
    public String getPattern(); 

    /**
     * Gets the on invalid.
     *
     * @return the on invalid
     */
    public String getOnInvalid();

    /**
     * Checks if is required.
     *
     * @return true, if is required
     */
    public boolean isRequired();

}
