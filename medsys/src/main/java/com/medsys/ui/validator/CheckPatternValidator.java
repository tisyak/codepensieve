/*
 * 
 */
package com.medsys.ui.validator;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.ui.validator.annotation.CheckPattern;


/**
 * The Class CheckPatternValidator.
 */
public class CheckPatternValidator implements
		ConstraintValidator<CheckPattern, String> {

	/** The pattern. */
	private CheckPattern pattern;

	/** The errors. */
	private ResourceBundle errors = ResourceBundle
			.getBundle("citizen/field-error-messages");

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(CheckPatternValidator.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(CheckPattern pattern) {
		this.pattern = pattern;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		
		//logger.debug("isValid called - value: " + value);
		if(pattern.loginFormUi().isRequired()){
			return checkNull(value, ctx) && checkMatched(value, ctx);
		}else{
			if(value!=null && !value.isEmpty()){
				return checkMatched(value, ctx);
			}else{
			
				/*This field is optional Hence, a null/empty value is a valid value*/
				return true;
				
			}
		}

	}

	/**
	 * Check null.
	 * 
	 * @param codeValue
	 *            the code value
	 * @param ctx
	 *            the ctx
	 * @return true, if successful
	 */
	private boolean checkNull(String codeValue, ConstraintValidatorContext ctx) {

		boolean isValid;
		
		
		
			String onInvalidMessage = errors.getString(pattern.loginFormUi()
		
				.getOnInvalid());
		
		if (codeValue == null || codeValue.equals("")) {
			isValid = false;
		} else {
			isValid = true;
		}

		if (!isValid) {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(onInvalidMessage)
					.addConstraintViolation();
		}
		
		return isValid;


	}

	/**
	 * Check matched.
	 * 
	 * @param codeValue
	 *            the code value
	 * @param ctx
	 *            the ctx
	 * @return true, if successful
	 */
	private boolean checkMatched(String codeValue,
			ConstraintValidatorContext ctx) {
	    String regex = this.pattern.loginFormUi()
                .getPattern();
	    logger.debug("Regex for field validation: " + regex);
	    String javaCompatibleRegex = regex.replace("\\\\","\\");
	    logger.debug("Java Compatible Regex for field validation: " + javaCompatibleRegex);
	    Pattern pattern = Pattern.compile(javaCompatibleRegex);
		Matcher matcher = pattern.matcher(codeValue);

		String onInvalidMessage = errors.getString(this.pattern.loginFormUi()
				.getOnInvalid());

		if (!matcher.matches()) {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(onInvalidMessage)
					.addConstraintViolation();
			return false;
		} else {
			return true;

		}

	}

}
