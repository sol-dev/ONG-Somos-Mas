package com.team32.ong.controller;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.service.TestimonialService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/testimonials")
public class TestimonialController {

    @Autowired
    TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialDto> createNewTestimonial(@RequestBody TestimonialDto newTestimonialDto) {
        TestimonialDto testimonialDtoCreated = testimonialService.save(newTestimonialDto);
        return new ResponseEntity(testimonialDtoCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> getTestimonials(@PageableDefault(size = 10) Pageable page) throws NotFoundException {
        return new ResponseEntity<>(testimonialService.getTestimonials(page), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial( @PathVariable Long id,
                                                             @RequestBody TestimonialDto testimonialDtoToUpdate) throws NotFoundException {
        TestimonialDto updatedTestimonial = testimonialService.updateById(testimonialDtoToUpdate, id);
        return new ResponseEntity<>(updatedTestimonial, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTestimonial(@PathVariable Long id) throws NotFoundException {
        testimonialService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
