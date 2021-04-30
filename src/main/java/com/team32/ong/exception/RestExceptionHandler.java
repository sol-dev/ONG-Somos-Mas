package com.team32.ong.exception;

import com.team32.ong.exception.custom.InvalidDataException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(NoSuchElementException exc){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return buildResponseEntity(httpStatus, exc);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(DuplicateKeyException exc){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildResponseEntity(httpStatus, exc);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exc){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildResponseEntity(httpStatus, exc);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(DataAccessException exc){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponseEntity(httpStatus, exc);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(InvalidDataException exc){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> errors =  exc.getResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return buildResponseEntity(httpStatus, new RuntimeException("Data Enviada Invalida"));
    }

    @ExceptionHandler
    protected  ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException exc){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildResponseEntity(httpStatus, new RuntimeException("tipo de Argumento invalido"));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleexception(Exception exc){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponseEntity(httpStatus, new RuntimeException("Error Interno - Reporte"));
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exc){
       return buildResponseEntity(httpStatus, exc, null);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exc,
                                                           List<String> errors){
       ErrorResponse error = new ErrorResponse();
       error.setMessage("Error - " + exc.getMessage());
       error.setStatus(httpStatus.value());
       error.setTimestamp(new Date());
       error.setErrors(errors);
     return new ResponseEntity(error,httpStatus);
    }
}