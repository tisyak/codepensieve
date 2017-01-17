/*
 * 
 */
package com.medsys.ui.util;


/**
 * The Enum UIValidation.
 */
public enum LoginFormUIValidation implements UIValidation{
	
	/*Order as per constructor:
	 * String pattern,boolean required,String onInvalid */
	
	/** The password.  (^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,30}$)  */
    password("(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*\\\\W)[a-zA-Z0-9\\\\S]{8,30}$)",true,"error.password.message"),
    
   
    /** The confirm password.*/
    confirmPassword("(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*\\\\W)[a-zA-Z0-9\\\\S]{8,30}$)",true,"error.confirmpassword.message"),

	/** The email address. using RFC5322*/
	//!#$%&'*+/=?^_`{|}~-  
	//email("(?=^.{5,254}$)((^[a-z0-9]+(?:\\\\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$))",false,"error.emailaddress.message"),
	email("(?=^.{5,254}$)((^[a-zA-Z0-9!#$%&*+/=?^_`{|}~-]+(?:\\\\.[a-zA-Z0-9!#$%&*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9]*[a-zA-Z0-9])?\\\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9]*[a-zA-Z0-9])?$))",false,"error.emailaddress.message"),
	
			
    invalid(null,false,null);
	
	/** The pattern. */
    private final String pattern;
    
    /** The on invalid. */
    private final String onInvalid;
    
    /** The required. */
    private final boolean required;
    
    /**
     * Instantiates a new UI validation.
     *
     * @param minLength the min length
     * @param maxLength the max length
     * @param pattern the pattern
     * @param required the required
     * @param onInvalid the on invalid
     */
    LoginFormUIValidation(String pattern,boolean required,String onInvalid) {
        this.pattern = pattern;
        this.required = required;
        this.onInvalid = onInvalid;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
        
      

    @Override
    public String getOnInvalid() {
        return this.onInvalid;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }
    

}
