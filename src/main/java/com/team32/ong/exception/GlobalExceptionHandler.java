package com.team32.ong.advice;

import com.team32.ong.exception.EmptyInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){
        return new ResponseEntity<String>("Input field is Empty, Please look into it", HttpStatus.BAD_REQUEST);
    }
}
