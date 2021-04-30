package com.team32.ong.controller;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.model.Testimonial;
import com.team32.ong.service.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<TestimonialDto> saveNewTestimonial(@Valid @RequestBody TestimonialDto newTestimonialDto, BindingResult result) {

        Testimonial testimonial = dtoToEntity(newTestimonialDto);

        Testimonial testimonialCreated = testimonialService.save(testimonial);

        return ResponseEntity.of(Optional.of(modelToDto(testimonialCreated)));
    }

    private TestimonialDto modelToDto(Testimonial testimonial){

        ModelMapper mapper = new ModelMapper();
        TestimonialDto map = mapper.map(testimonial, TestimonialDto.class);

        return map;
    }

    private Testimonial dtoToEntity(TestimonialDto testimonialDto){

        ModelMapper mapper = new ModelMapper();
        Testimonial map = mapper.map(testimonialDto, Testimonial.class);

        return map;
    }
}
