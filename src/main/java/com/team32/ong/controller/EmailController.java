package com.team32.ong.controller;

import com.team32.ong.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam(value = "emailRequest") String emailrequest){
        System.out.println(emailrequest);
        return new ResponseEntity<>(emailService.sendEmail(emailrequest), HttpStatus.OK);
    }
}
