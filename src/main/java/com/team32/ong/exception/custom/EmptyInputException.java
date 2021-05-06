package com.team32.ong.exception.custom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.Data;

@Data
public class EmptyInputException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public EmptyInputException(BindingResult result) {
		super(getAllErrors(result));
	}

	private static String getAllErrors(BindingResult result) {
		StringBuffer errorsBuffer = new StringBuffer();
		List<String>  errorFound =  result.getAllErrors()
											.stream()
											.map(error -> error.getDefaultMessage())
											.collect(Collectors.toList());
		errorFound.forEach(error -> errorsBuffer.append(error));
		return errorsBuffer.toString();
	}
}
