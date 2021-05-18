package com.team32.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.ContactDTO;
import com.team32.ong.service.ContactService;

@RestController
@RequestMapping("api/v1/contacts")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts")
	public ResponseEntity<List<ContactDTO>> getAll(){
		return new ResponseEntity<List<ContactDTO>>(contactService.getAll(),HttpStatus.OK);
	}

}
