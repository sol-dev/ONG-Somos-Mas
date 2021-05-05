package com.team32.ong.exception.custom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(BindingResult result) {
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