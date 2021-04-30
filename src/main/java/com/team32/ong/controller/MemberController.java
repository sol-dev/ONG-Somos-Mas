package com.team32.ong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
		
		Member member = toMember(memberDTO);
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
				.stream()
				.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
				.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			member = memberService.createMember(member);
		} catch (DataAccessException e) {
			response.put("msj", "Error al guardar miembro.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El miembro ha sido registrado.");
		response.put("member", toMemberDTO(member));
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	public Member toMember(MemberDTO memberDTO) {
		ModelMapper mapper = new ModelMapper();
		Member member = mapper.map(memberDTO, Member.class);
		return member;
	}
	
	public MemberDTO toMemberDTO(Member member) {
		ModelMapper mapper = new ModelMapper();
		MemberDTO memberDTO = mapper.map(member, MemberDTO.class);
		return memberDTO;
	}
	
}
