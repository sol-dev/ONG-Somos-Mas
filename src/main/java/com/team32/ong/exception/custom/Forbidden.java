package com.team32.ong.exception.custom;

public class Forbidden extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public Forbidden(String stringError) {
		super(stringError);
	}

}
