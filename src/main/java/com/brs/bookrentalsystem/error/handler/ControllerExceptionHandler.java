package com.brs.bookrentalsystem.error.handler;


import com.brs.bookrentalsystem.error.ErrorResponse;
import com.brs.bookrentalsystem.error.exception.BaseException;
import com.brs.bookrentalsystem.error.exception.impl.NoSuchEntityFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;


@Controller
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ModelAndView handleUserException(final NoSuchEntityFoundException ex){
        ErrorResponse errorResponse = null;

        if(ex != null){
            errorResponse = new ErrorResponse("BAD_REQUEST",ex.getLocalizedMessage());
        }
        logger.error("Bad user request");

        ModelAndView mv = new ModelAndView();
        mv.addObject("message", errorResponse );
        mv.setViewName("exception-page");
        return mv;
    }


    // handle foreign key and unique key violation exception
    @ExceptionHandler({DataIntegrityViolationException.class})
    public String handleDatabaseException(final DataIntegrityViolationException ex,
                                          HttpServletRequest request,
                                          Model model,
                                          RedirectAttributes ra){
        ErrorResponse errorResponse = new ErrorResponse();

        String errorReceived = ex.getMessage();
        String requestedUrl = request.getRequestURI();

        if(errorReceived.contains("uk_")){
            errorResponse.setCode("S001");
            errorResponse.setMessage("Unique key Violation");
        } else if(errorReceived.contains("fk_")){
            errorResponse.setCode("S002");
            errorResponse.setMessage("Foreign key Violation");
        } else if(errorReceived.contains("null")){      // remove
            errorResponse.setCode("S003");
            errorResponse.setMessage("");
        } else {
            throw ex;
        }

        System.out.println("Context Path : " + request.getRequestURI());

        String redirectionURI = extractRequestUrl(requestedUrl);

        ra.addFlashAttribute("errorResponse", errorResponse);

        logger.error(errorResponse.getMessage());

        if (model.containsAttribute("authorRequest")) {
            System.out.println("Model " + model.getAttribute("authorRequest"));
        }

        return "redirect:" + redirectionURI;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ModelAndView handleUnAuthorizedRequestException(AccessDeniedException accessDeniedException){
        ErrorResponse errorResponse = new ErrorResponse("NOT_AUTHORIZED", "You do not have necessary permission to access this page");
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", errorResponse );
        mv.setViewName("exception-page");
        return mv;
    }


    private String extractRequestUrl(String requestedUrl){
        String url;
        if(requestedUrl.contains("/delete")){
            url = requestedUrl.substring(0, requestedUrl.indexOf("/delete") + 1);
        } else {
            url = requestedUrl.substring(0, requestedUrl.lastIndexOf("/") );
        }

        return url;
    }


}
