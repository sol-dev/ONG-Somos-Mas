package com.team32.ong.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Conflict;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;

import javassist.NotFoundException;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> notFoundException(Exception e, HttpServletRequest req){
    	ErrorResponse errorFound = new ErrorResponse(404, new Date(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errorFound, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({
    	BadRequestException.class,
    	DuplicateKeyException.class,
    	MethodArgumentTypeMismatchException.class,
    	EmptyInputException.class
    })
    protected ResponseEntity<?> badRequestException(Exception e, HttpServletRequest req){
    	ErrorResponse errorFound = new ErrorResponse(400, new Date(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errorFound, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Forbidden.class)
    protected ResponseEntity<?> forbiddenException(Exception e, HttpServletRequest req){
    	ErrorResponse errorFound = new ErrorResponse(403, new Date(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errorFound, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(Conflict.class)
    protected ResponseEntity<?> conflictException(Exception e, HttpServletRequest req){
    	ErrorResponse errorFound = new ErrorResponse(409, new Date(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errorFound, HttpStatus.CONFLICT);
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
    	Unauthorized.class,
    	AccessDeniedException.class
    })
    protected void unauthorizedException(Exception e, HttpServletRequest req){
    }
    
    @ExceptionHandler({
    	IOException.class,
    	Exception.class    	
    })
    protected ResponseEntity<?> exception(Exception e, HttpServletRequest req){
    	ErrorResponse errorFound = new ErrorResponse(500, new Date(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errorFound, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Falta el parámetro " + ex.getParameterName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "No se puede responder a la petición porque faltan parámetros", error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
}


