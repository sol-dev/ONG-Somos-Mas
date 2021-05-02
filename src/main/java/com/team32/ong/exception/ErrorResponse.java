package com.team32.ong.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private Date timestamp;
    private List<String> errors;

    ErrorResponse(String message){
        this.message = message;
    }
}
