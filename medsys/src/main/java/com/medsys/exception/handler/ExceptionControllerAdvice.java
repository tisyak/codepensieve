package com.medsys.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medsys.ui.util.UIConstants;

/**
 * The Class ExceptionControllerAdvice.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(ExceptionControllerAdvice.class);
   
    
    //TODO : Cini - How to call child exception handler when parent exception handler exists
    
    /**
    * Exception.
    *
    * @param e the e
    * @return the string
    */
    @ResponseStatus(HttpStatus.CONFLICT)  
    // HttpStatus.CONFLICT 409 code is used in situations where the user 
    // might be able to resolve the conflict and resubmit the request.
    //TODO: Figure out alternative for runtime exception loading
    @ResponseBody
    @ExceptionHandler(Exception.class)
   public String exception(Exception e,HttpServletRequest request) {
        logger.debug("Inside MEDSYS exception handler.Exception recieved: " + e.getMessage());
        request.setAttribute(UIConstants.MSG_FOR_SYSTEM_ERROR.getValue(), e.getMessage());
        return e.getMessage();
    }

}
