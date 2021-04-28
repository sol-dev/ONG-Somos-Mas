package com.team32.ong.controller;

import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @PostMapping("/testimonials")
    Testimonial newEmployee(@Valid @RequestBody Testimonial newTestimonial) {
        return testimonialRepository.save(newTestimonial);
    }
}
