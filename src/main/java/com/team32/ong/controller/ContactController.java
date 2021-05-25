package com.team32.ong.controller;

import java.io.IOException;
import java.util.List;
import com.team32.ong.dto.ContactDTO;
import com.team32.ong.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/backoffice/contacts")
    public ResponseEntity<List<ContactDTO>> getAll(){
        return new ResponseEntity<List<ContactDTO>>(contactService.getAll(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO contactDTO) throws IOException {
        return new ResponseEntity<>(contactService.save(contactDTO), HttpStatus.CREATED);
    }
}