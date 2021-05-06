package com.team32.ong.service;


import com.team32.ong.dto.TestimonialDto;

public interface TestimonialService {
    TestimonialDto save(TestimonialDto testimonialDto);
    TestimonialDto updateById(TestimonialDto testimonialDto, Long id);
}
