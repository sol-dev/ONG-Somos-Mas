package com.team32.ong.exception.custom;

public class InvalidDataException extends RuntimeException{

    private final static long serialVersionUID = 1L;

    public InvalidDataException(String message){
        super(message);
    }
}
