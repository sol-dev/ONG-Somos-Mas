package com.team32.ong.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.IMemberService;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {
    
	@Autowired
	IMemberService memberService;
	
	@PostMapping
    public ResponseEntity<MemberDTO> createActivity(@Valid @RequestBody MemberDTO newMemberDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
    	MemberDTO memberCreate = memberService.save(newMemberDTO);
        response.put("message", "Miembro Guardado con exito!");
        response.put("member", memberCreate);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
	
}
