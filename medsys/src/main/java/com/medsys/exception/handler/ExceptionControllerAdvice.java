package com.medsys.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.medsys.exception.SysException;

/**
 * The Class ExceptionControllerAdvice.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(ExceptionControllerAdvice.class);
   //@ExceptionHandler(SysException.class)
    
    //TODO : Cini - How to call child exception handler when parent exception handler exists
    
    /**
    * Exception.
    *
    * @param e the e
    * @return the string
    */
   public String exception(SysException e) {
        logger.debug("Inside MEDSYS exception handler.");
        return "redirect:/logout";
    }

}
