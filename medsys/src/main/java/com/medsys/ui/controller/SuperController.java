/*
 * 
 */
package com.medsys.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The Class SuperController.
 */
@Controller
@SessionAttributes({ "adminUserToken"})
@Configuration
@PropertySources({
	 	@PropertySource(value = "classpath:email-messages.properties"),
        @PropertySource(value = "classpath:mobile-messages.properties"),
        @PropertySource(value = "classpath:ui-messages.properties"),
        @PropertySource(value = "classpath:labels.properties"),
        @PropertySource(value = "classpath:system-error-messages.properties"),
        @PropertySource(value = "classpath:ValidationMessages.properties")})
public class SuperController {

	/** The environment. */
    @Autowired
    Environment environment;

    /**
     * Property sources placeholder configurer.
     * 
     * @return the property sources placeholder configurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Inits the binder.
     * 
     * @param binder
     *            This method ensure that all form fields that are submitted as
     *            empty string / only whitespaces get converted to null so that
     *            the javax.validation.constraints can work as expected
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    
}
