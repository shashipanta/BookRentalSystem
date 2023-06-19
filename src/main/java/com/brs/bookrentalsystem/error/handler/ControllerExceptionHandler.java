package com.brs.bookrentalsystem.error.handler;


import com.brs.bookrentalsystem.error.ErrorResponse;
import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import com.brs.bookrentalsystem.error.exception.NoSuchElementFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoSuchElementFoundException.class)
    public ModelAndView handleUserException(final NoSuchElementFoundException ex){
        ErrorResponse errorResponse = null;
        if(ex instanceof NoSuchElementFoundException){
            errorResponse = new ErrorResponse(ex.getLocalizedMessage(), ErrorCodes.AUTHOR_NOT_FOUND.name());
        }
        logger.error("Bad user request");

        ModelAndView mv = new ModelAndView();
        mv.addObject("errorResponse", errorResponse );
        mv.setViewName("exception-page");
        return mv;
    }

}
