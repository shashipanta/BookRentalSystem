package com.brs.bookrentalsystem.error.handler;


import com.brs.bookrentalsystem.error.ErrorResponse;
import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import com.brs.bookrentalsystem.error.exception.NoSuchElementFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
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

    @ExceptionHandler(Exception.class)
    public String handleDatabaseException(final Exception ex,
                                          HttpServletRequest request,
                                          RedirectAttributes ra){
        ErrorResponse errorResponse = new ErrorResponse();


        System.out.println("Context Path : " + request.getContextPath());

        if(ex instanceof DataIntegrityViolationException){
            errorResponse.setCode("S001");
            errorResponse.setMessage("foreign key violation");

            ra.addFlashAttribute("errorResponse", errorResponse);
//            mv.setViewName("brs/admin/author/");
//            mv.setViewName("author/author-page");
        }
        logger.error("foreign key violation exception occured");

        return "redirect:/brs/admin/author/";
    }


}
