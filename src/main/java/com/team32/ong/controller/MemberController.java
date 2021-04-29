package com.team32.ong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.model.Member;
import com.team32.ong.service.MemberService;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {
    
	@Autowired
	MemberService memberService;
	
	@PostMapping
	public ResponseEntity<?> saveMember(@Valid @RequestBody MemberDTO memberDTO, BindingResult result){
		
		MemberDTO member = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
				//Se convierte en stream
				.stream()
				//Cada error se convierte en un string
				.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
				//Se convierte en una lista
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			member = memberService.createMember(memberDTO);
		} catch (DataAccessException e) {
			response.put("msj", "Failed to save user.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","The customer has registered.");
		response.put("member", member);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
