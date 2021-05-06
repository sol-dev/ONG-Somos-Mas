package com.team32.ong.service;


import com.team32.ong.dto.TestimonialDto;
import javassist.NotFoundException;

public interface TestimonialService {
    TestimonialDto save(TestimonialDto testimonialDto);
    TestimonialDto updateById(TestimonialDto testimonialDto, Long id) throws NotFoundException;
}
