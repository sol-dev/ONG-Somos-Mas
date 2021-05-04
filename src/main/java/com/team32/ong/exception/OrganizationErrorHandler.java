package com.team32.ong.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrganizationErrorHandler {

    @ExceptionHandler
    public ResponseEntity<OrganizationErrorInfo> handlerIllegalArgumentException (NoSuchElementException exception){
        OrganizationErrorInfo error = new OrganizationErrorInfo( "Error no se encontro la entidad en la base",404);
        return new ResponseEntity<OrganizationErrorInfo>(error, HttpStatus.NOT_FOUND);
    }
 
    @ExceptionHandler
    public ResponseEntity<OrganizationErrorInfo> handlerGeneralException (Exception exception){
        OrganizationErrorInfo error = new OrganizationErrorInfo( "Error en la validacion",400);
        return new ResponseEntity<OrganizationErrorInfo>(error, HttpStatus.BAD_REQUEST);
    }
}
