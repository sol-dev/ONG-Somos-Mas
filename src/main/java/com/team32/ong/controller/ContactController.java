package com.team32.ong.controller;

import com.team32.ong.dto.ContactDTO;
import com.team32.ong.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/backoffice/contacts")
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }
}
