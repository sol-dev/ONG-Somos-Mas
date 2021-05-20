package com.team32.ong.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.event.S3EventNotification.ResponseElementsEntity;
import com.team32.ong.dto.MemberDTO;
import com.team32.ong.service.IMemberService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {
    
	@Autowired
	IMemberService memberService;
	
	@PostMapping
    public ResponseEntity<MemberDTO> createActivity(@Valid @RequestBody MemberDTO newMemberDTO){
		return new ResponseEntity<MemberDTO>(memberService.save(newMemberDTO), HttpStatus.CREATED);
    }
	
	@GetMapping
	public ResponseEntity<?> getAll(){
        List<MemberDTO> list = memberService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
