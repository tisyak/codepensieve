/*
 * 
 */
package com.medsys.ui.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.medsys.ui.util.LoginFormUIValidation;
import com.medsys.ui.validator.CheckPatternValidator;



/**
 * The Interface CheckPattern.
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPatternValidator.class)
public @interface CheckPattern {
	
	/**
	 * Message.
	 *
	 * @return the string
	 */
	String message() default "";
	
	/**
	 * Groups.
	 *
	 * @return the class[]
	 */
	Class<?>[] groups() default {};
	
	/**
	 * Payload.
	 *
	 * @return the class<? extends payload>[]
	 */
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * Ui.
	 *
	 * @return the UI validation
	 */
	
	LoginFormUIValidation loginFormUi() default LoginFormUIValidation.invalid;

}
