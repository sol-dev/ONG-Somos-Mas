package com.team32.ong.component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Validation {
	
	public boolean validateEmail(String email) {
		Pattern regex = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
		Matcher m = regex.matcher(email);
		return m.find() ? true : false;
	}

	public Boolean stringIsOnlyDigits(String str){
        String regex = "[0-9]+";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
    public Boolean stringHasDigit(String str){
        Boolean hasDigit = false;
        for(int i = 0; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                hasDigit = true;
                break;
            }
        }
        return  hasDigit;
    }

    public boolean isNotBlankNotEmpty(String string){
        boolean valid = false;
        if(string.length() >0 || !string.isBlank())
            valid = true;
        return valid;
    }
}