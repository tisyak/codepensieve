/*
 * 
 */
package com.medsys.ui.model;


import org.hibernate.validator.constraints.NotEmpty;

import com.medsys.ui.util.LoginFormUIValidation;
import com.medsys.ui.validator.annotation.CheckPattern;

/**
 * The Class UserForm.
 */
public class LoginForm {

    /** The given name. */
    @NotEmpty
    @CheckPattern(loginFormUi = LoginFormUIValidation.email)
    private String username;

    /** The password. */
    @CheckPattern(loginFormUi = LoginFormUIValidation.password)
    private String password;

    private boolean addCaptcha;

    /** The captcha. */
    private String captcha;

    /** The j_captcha_response. */
    private String j_captcha_response;
    
    

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public boolean isAddCaptcha() {
		return addCaptcha;
	}



	public void setAddCaptcha(boolean addCaptcha) {
		this.addCaptcha = addCaptcha;
	}



	public String getCaptcha() {
		return captcha;
	}



	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}



	public String getJ_captcha_response() {
		return j_captcha_response;
	}



	public void setJ_captcha_response(String j_captcha_response) {
		this.j_captcha_response = j_captcha_response;
	}



	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password
				+ "]";
	}
    
    


}
