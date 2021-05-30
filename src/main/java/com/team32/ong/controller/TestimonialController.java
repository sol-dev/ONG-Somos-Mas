package com.team32.ong.controller;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.service.TestimonialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/testimonials")
@Api(value = "Testimonials microservice", tags = "Testimonial controller")
public class TestimonialController {

    @Autowired
    TestimonialService testimonialService;

    @PostMapping
    @ApiOperation(value = "Create a testimonial", response = TestimonialDto.class)
    public ResponseEntity<TestimonialDto> createNewTestimonial(@RequestBody TestimonialDto newTestimonialDto) {
        TestimonialDto testimonialDtoCreated = testimonialService.save(newTestimonialDto);
        return new ResponseEntity(testimonialDtoCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a testimonial", notes = "Update a testimonial by Id", response = TestimonialDto.class)
    public ResponseEntity<TestimonialDto> updateTestimonial( @PathVariable Long id,
                                                             @RequestBody TestimonialDto testimonialDtoToUpdate) throws NotFoundException {
        TestimonialDto updatedTestimonial = testimonialService.updateById(testimonialDtoToUpdate, id);
        return new ResponseEntity<>(updatedTestimonial, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a testimonial", notes = "Delete a testimonial by Id")
    public ResponseEntity<?> deleteTestimonial(@PathVariable Long id) throws NotFoundException {
        testimonialService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
