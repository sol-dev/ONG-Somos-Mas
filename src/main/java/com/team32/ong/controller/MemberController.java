package com.team32.ong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.service.IMemberService;

import javassist.NotFoundException;

@RestController
@RequestMapping("api/v1/member")
public class MemberController {
    
	@Autowired
	IMemberService memberService;
	
	@PostMapping
    public ResponseEntity<MemberDTO> createActivity(@Valid @RequestBody MemberDTO newMemberDTO){
		return new ResponseEntity<MemberDTO>(memberService.save(newMemberDTO), HttpStatus.CREATED);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateTestimonial( @PathVariable Long id,
                                                             @RequestBody MemberDTO testimonialDtoToUpdate) throws NotFoundException {
        MemberDTO updatedTestimonial = memberService.updateById(testimonialDtoToUpdate, id);
        return new ResponseEntity<>(updatedTestimonial, HttpStatus.OK);
    }
	
}
