package com.team32.ong.controller;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/testimonials")
public class TestimonialController {

    @Autowired
    TestimonialService testimonialService;

    public ResponseEntity<TestimonialDto> saveNewTestimonial(@Valid @RequestBody TestimonialDto newTestimonialDto,
                                                             BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity(result.getFieldError().toString(), HttpStatus.BAD_REQUEST);
        }

        TestimonialDto dtoSaved = null;

        try{

            dtoSaved = testimonialService.save(newTestimonialDto);

            return ResponseEntity.of(Optional.of(dtoSaved));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
