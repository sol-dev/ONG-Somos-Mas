package com.team32.ong.controller;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/testimonials")
public class TestimonialController {

    @Autowired
    TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialDto> createNewTestimonial(@Valid @RequestBody TestimonialDto newTestimonialDto, BindingResult result) {

        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }

        TestimonialDto testimonialDtoCreated = testimonialService.save(newTestimonialDto);

        return new ResponseEntity(testimonialDtoCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTestimonial(@PathVariable Long id){
        testimonialService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
