package com.team32.ong.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e, WebRequest req) {
		ErrorResponse  errorFound = new ErrorResponse(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(errorFound, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}