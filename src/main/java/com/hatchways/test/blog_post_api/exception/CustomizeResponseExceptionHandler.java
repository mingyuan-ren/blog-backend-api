package com.hatchways.test.blog_post_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizeResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException
            (MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>("\"error: \""+ex.getName()+" parameter is invalid!", HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    protected final ResponseEntity<Object> handleMissingServletRequestParameterException
//            (MissingServletRequestParameterException ex) {
//        return new ResponseEntity<>("\"error: \""+ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
}
